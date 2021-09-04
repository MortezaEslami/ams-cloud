package com.sample.ams.service;

import com.sample.ams.exception.BadRequestInfoException;
import com.sample.ams.exception.ResourceNotFoundException;
import com.sample.ams.model.BankAccount;
import com.sample.ams.model.dto.BankAccountDTO;
import com.sample.ams.model.enumeration.AccountStatus;
import com.sample.ams.model.enumeration.AccountType;
import com.sample.ams.repository.BankAccountRepository;
import com.sample.ams.service.Interface.IBankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BankAccountService extends GenericService<BankAccount, Long, BankAccountDTO.Info, BankAccountDTO.Create, BankAccountDTO.Update> implements IBankAccountService {

    private final BankAccountRepository bankAccountRepository;

    @Override
    public BankAccountDTO.Info create(BankAccountDTO.Create request) {
        request.setAccountStatus(AccountStatus.Open);
        List<BankAccountDTO.Info> bankAccountList = getByCustomer(request.getCustomerId());
        if (!bankAccountList.isEmpty()) {
            if (bankAccountList.size() == 2) {
                //bad request two account (one account for each account type)
                throw new BadRequestInfoException("  This customer has max bank account (one account for each account type). customerId =  " + request.getCustomerId());
            } else if (bankAccountList.get(0).getAccountType().equals(request.getAccountType())) {
                // this customer has account on this account type
                throw new BadRequestInfoException(" This customer has account on this account type , customerId =  " + request.getCustomerId());
            }
        }
        return super.create(request);
    }

    @Override
    public BankAccountDTO.Info getByAccountNumber(String accountNumber) {
        Optional<BankAccount> bankAccountOptional = bankAccountRepository.findByAccountNumber(accountNumber);
        BankAccount bankAccount = bankAccountOptional.orElseThrow(() -> new ResourceNotFoundException(" This account number " + accountNumber + " is not exist."));
        return modelMapper.map(bankAccount, BankAccountDTO.Info.class);
    }

    @Override
    public void deposit(String destinationAccountNumber, BigDecimal depositAmount) {
        bankAccountRepository.deposit(destinationAccountNumber, depositAmount);
    }

    @Override
    public void withdraw(String originAccountNumber, BigDecimal withdrawAmount) {
        bankAccountRepository.withdraw(originAccountNumber, withdrawAmount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankAccountDTO.Info> getByCustomer(Long id) {
        List<BankAccount> bankAccountList = bankAccountRepository.findByCustomerId(id);
        return bankAccountList.stream().map(item ->
                modelMapper.map(item, BankAccountDTO.Info.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankAccountDTO.Info> getByAccountNumberContaining(String accountNumber) {
        List<BankAccount> bankAccountList = bankAccountRepository.findByAccountNumberContaining(accountNumber);
        return bankAccountList.stream().map(item ->
                modelMapper.map(item, BankAccountDTO.Info.class))
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public List<BankAccountDTO.Info> getByCardNumberContaining(String cardNumber) {
        List<BankAccount> bankAccountList = bankAccountRepository.findByCardNumberContaining(cardNumber);
        return bankAccountList.stream().map(item ->
                modelMapper.map(item, BankAccountDTO.Info.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankAccountDTO.Info> getByAccountType(AccountType accountType) {
        List<BankAccount> bankAccountList = bankAccountRepository.findByAccountType(accountType);
        return bankAccountList.stream().map(item ->
                modelMapper.map(item, BankAccountDTO.Info.class))
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public List<BankAccountDTO.Info> getByAccountStatus(AccountStatus accountStatus) {
        List<BankAccount> bankAccountList = bankAccountRepository.findByAccountStatus(accountStatus);
        return bankAccountList.stream().map(item ->
                modelMapper.map(item, BankAccountDTO.Info.class))
                .collect(Collectors.toList());
    }


    @Override
    public BankAccountDTO.Info changeStatus(String accountNumber, AccountStatus accountStatus) {
        Optional<BankAccount> bankAccountOptional = bankAccountRepository.findByAccountNumber(accountNumber);
        BankAccount bankAccount = bankAccountOptional.orElseThrow(() -> new ResourceNotFoundException(" This account number " + accountNumber + " is not exist."));
        if (bankAccount.getAccountNumber().equals(accountStatus)) {
            throw new BadRequestInfoException(" this account has your request status. Account Number =  " + accountNumber);
        }
        if (bankAccount.getAccountStatus().equals(AccountStatus.Close)) {
            throw new BadRequestInfoException(" Account is close . It couldn't be changed . account Number =  " + accountNumber);
        }
        bankAccount.setAccountStatus(accountStatus);
        bankAccount = bankAccountRepository.save(bankAccount);
        return modelMapper.map(bankAccount, BankAccountDTO.Info.class);
    }


}

