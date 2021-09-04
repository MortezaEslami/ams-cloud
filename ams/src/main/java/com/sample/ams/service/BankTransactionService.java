package com.sample.ams.service;

import com.google.gson.Gson;
import com.sample.ams.exception.BadRequestInfoException;
import com.sample.ams.exception.ResourceNotFoundException;
import com.sample.ams.model.BankTransaction;
import com.sample.ams.model.dto.BankAccountDTO;
import com.sample.ams.model.dto.BankTransactionDTO;
import com.sample.ams.model.enumeration.AccountStatus;
import com.sample.ams.model.enumeration.CurrencyType;
import com.sample.ams.model.enumeration.TransactionStatus;
import com.sample.ams.model.enumeration.TransactionType;
import com.sample.ams.proxy.CurrencyExchangeRateProxy;
import com.sample.ams.proxy.ResultApiCall;
import com.sample.ams.repository.BankTransactionRepository;
import com.sample.ams.service.Interface.IBankAccountService;
import com.sample.ams.service.Interface.IBankTransactionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BankTransactionService extends GenericService<BankTransaction, Long, BankTransactionDTO.Info, BankTransactionDTO.Create, BankTransactionDTO.Update> implements IBankTransactionService {

    private final IBankAccountService bankAccountService;
    private final BankTransactionRepository bankTransactionRepository;
    private final CurrencyExchangeRateProxy proxy;

    @Value("${api.key}")
    private String key;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    float exchangeRate;

    @Override
    @Transactional
    public BankTransactionDTO.Info deposit(String destinationAccountNumber, BigDecimal amount, CurrencyType currencyType) {
        logger.info(" Request deposit to {} amount {}  currency type {} .", destinationAccountNumber, amount, currencyType);
        BankAccountDTO.Info bankAccountDTODestination = bankAccountService.getByAccountNumber(destinationAccountNumber);
        if (!(bankAccountDTODestination.getAccountStatus().equals(AccountStatus.Open))) {
            throw new BadRequestInfoException("  accounts status should be open ! otm=  " + bankAccountDTODestination.getAccountStatus());
        }
        BigDecimal depositAmount = amount;
        if (!currencyType.equals(bankAccountDTODestination.getCurrencyType())) {
            exchangeRate = currencyExchangeRate();
            if (!currencyType.equals(bankAccountDTODestination.getCurrencyType())) {
                depositAmount = convertAmount(currencyType, amount, exchangeRate);
            }
        }
        bankAccountService.deposit(destinationAccountNumber, depositAmount);

        return saveBankTransaction(amount, depositAmount, currencyType, null,
                destinationAccountNumber, TransactionType.Deposit, TransactionStatus.Success);
    }


    @Override
    @Transactional
    public BankTransactionDTO.Info withdraw(String originAccountNumber, BigDecimal amount, CurrencyType currencyType) {
        logger.info(" Request withdraw from {} amount {}  currency type {} .", originAccountNumber, amount, currencyType);
        BankAccountDTO.Info bankAccountDTOOrigin = bankAccountService.getByAccountNumber(originAccountNumber);
        if (!(bankAccountDTOOrigin.getAccountStatus().equals(AccountStatus.Open))) {
            throw new BadRequestInfoException("  accounts status should be open ! from=  " + bankAccountDTOOrigin.getAccountStatus());
        }
        BigDecimal withdrawAmount = amount;
        if (!currencyType.equals(bankAccountDTOOrigin.getCurrencyType())) {
            exchangeRate = currencyExchangeRate();
            if (!currencyType.equals(bankAccountDTOOrigin.getCurrencyType())) {
                withdrawAmount = convertAmount(currencyType, amount, exchangeRate);
            }
        }
        if (bankAccountDTOOrigin.getBalance().compareTo(withdrawAmount) < 0) {
            throw new BadRequestInfoException(" balance is not enough for this amount withdraw " + withdrawAmount);
        }
        bankAccountService.withdraw(originAccountNumber, withdrawAmount);

        return saveBankTransaction(amount, withdrawAmount, currencyType, originAccountNumber,
                null, TransactionType.Withdraw, TransactionStatus.Success);
    }


    @Override
    @Transactional
    public BankTransactionDTO.Info transfer(String from, String to, BigDecimal amount, CurrencyType currencyType) {
        logger.info(" Request transfer from {} to {} amount {} currency type {} .", from, to, amount, currencyType);
        if (from.equals(to))
            throw new BadRequestInfoException("  Both of account numbers are identical");
        BankAccountDTO.Info bankAccountDTOOrigin = bankAccountService.getByAccountNumber(from);
        BankAccountDTO.Info bankAccountDTODestination = bankAccountService.getByAccountNumber(to);
        if (!(bankAccountDTOOrigin.getAccountStatus().equals(AccountStatus.Open) &&
                bankAccountDTODestination.getAccountStatus().equals(AccountStatus.Open))) {
            throw new BadRequestInfoException(" Both accounts status should be open !. from=  " + bankAccountDTOOrigin.getAccountStatus() +
                    "to = " + bankAccountDTODestination.getAccountStatus());
        }
        BigDecimal withdrawAmount, depositAmount;
        withdrawAmount = depositAmount = amount;
        if (!(currencyType.equals(bankAccountDTOOrigin.getCurrencyType()) &&
                currencyType.equals(bankAccountDTODestination.getCurrencyType()))) {
            exchangeRate = currencyExchangeRate();
            BigDecimal convertedAmount = convertAmount(currencyType, amount, exchangeRate);
            if (!currencyType.equals(bankAccountDTOOrigin.getCurrencyType())) {
                withdrawAmount = convertedAmount;
            }
            if (bankAccountDTOOrigin.getBalance().compareTo(withdrawAmount) < 0) {
                throw new BadRequestInfoException(" balance is not enough for this amount withdraw " + withdrawAmount);
            }

            if (!currencyType.equals(bankAccountDTODestination.getCurrencyType())) {
                depositAmount = convertedAmount;
            }
        }
        bankAccountService.withdraw(from, withdrawAmount);
        bankAccountService.deposit(to, depositAmount);
        return saveBankTransaction(amount, null, currencyType, from,
                to, TransactionType.Transfer, TransactionStatus.Success);
    }


    @Override
    public List<BankTransactionDTO.Info> retrieveTransactionLogs(String startDate, String endDate, String accountNumber, Integer pageNumber, Integer pageCount, Sort.Direction orderType) {
        logger.info(" Request for retrieving transaction log by start date {} , end date {} , account number {}",
                startDate, endDate, accountNumber);
        Date date1, date2;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            date2 = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
        } catch (ParseException e) {
            throw new BadRequestInfoException(" Start date or End date dont have correct format. (2022-02-02)");
        }
        Pageable pageable =
                PageRequest.of(pageNumber, pageCount,
                        orderType.isAscending() ? Sort.by("transactionTime").ascending() : Sort.by("transactionTime").descending());
        List<BankTransaction> bankTransactionList;
        if (accountNumber == null) {
            bankTransactionList = bankTransactionRepository
                    .getAllTransactionBetweenDates(date1, date2, pageable);
        } else {
            bankTransactionList = bankTransactionRepository
                    .getAllTransactionBetweenDatesByAccountNumber(date1, date2, accountNumber, pageable);
        }

        return bankTransactionList.stream().map(item ->
                modelMapper.map(item, BankTransactionDTO.Info.class))
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public BankTransactionDTO.Info getByTrackingCode(String trackingCode) {
        Optional<BankTransaction> bankTransactionOptional = bankTransactionRepository.findByTrackingCode(trackingCode);
        logger.info(" Request for retrieving transaction log by tracking code {}", trackingCode);
        BankTransaction bankTransaction = bankTransactionOptional.orElseThrow(() -> new ResourceNotFoundException(" This tracking code " + trackingCode + " is not exist."));
        return modelMapper.map(bankTransaction, BankTransactionDTO.Info.class);
    }


    public BigDecimal convertAmount(CurrencyType transactionCurrencyType, BigDecimal amount, float exchangeRate) {
        BigDecimal convertedAmount;
        if (transactionCurrencyType.equals(CurrencyType.Eur)) {
            convertedAmount = amount.multiply(BigDecimal.valueOf(exchangeRate));
        } else {
            convertedAmount = amount.multiply(BigDecimal.valueOf(1 / exchangeRate));
        }
        return convertedAmount;
    }


    public BankTransactionDTO.Info saveBankTransaction(BigDecimal amount, BigDecimal convertedAmount, CurrencyType currencyType,
                                                       String from, String to, TransactionType transactionType, TransactionStatus transactionStatus) {
        BankTransactionDTO.Create bankTransactionDTO = new BankTransactionDTO.Create();
        bankTransactionDTO.setAmount(amount);
        bankTransactionDTO.setAmount(convertedAmount);
        bankTransactionDTO.setCurrencyType(currencyType);
        bankTransactionDTO.setOriginAccountNumber(from);
        bankTransactionDTO.setDestinationAccountNumber(to);
        bankTransactionDTO.setTransactionType(transactionType);
        bankTransactionDTO.setTransactionStatus(transactionStatus);
        bankTransactionDTO.setTransactionTime(new Date());
        bankTransactionDTO.setTrackingCode(Instant.now().toEpochMilli() + (from != null ? from : to));
        BankTransaction bankTransaction = modelMapper.map(bankTransactionDTO, BankTransaction.class);
        bankTransaction = bankTransactionRepository.save(bankTransaction);
        logger.info(" Transaction {} done from account {} to {}", transactionType, from, to);
        return modelMapper.map(bankTransaction, BankTransactionDTO.Info.class);
    }


    public String getLastRate() {
        // http://data.fixer.io/api/latest?access_key=419c3c0528ccc8d975cbde7083241503&symbols=USD
        final String uri = "http://data.fixer.io/api/latest?access_key=" + key + "&&symbols=USD";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        System.out.println(result);
        return result;
    }


    public float currencyExchangeRate() {
        Gson g = new Gson();
        ResultApiCall resultApiCall;
        int i = 0;
        float exchangeRate = 0;
        while (i++ < 10) {
            resultApiCall = g.fromJson(proxy.currencyExchangeRate(key, "USD"), ResultApiCall.class);
            if (resultApiCall.isSuccess()) {
                exchangeRate = Float.parseFloat(resultApiCall.getRates().getUSD());
                break;
            }

        }
        logger.info(" calling currency exchange rate feign method and exchange rate rate is {} for exchange Euro to USD", exchangeRate);
        return exchangeRate;
    }
}

