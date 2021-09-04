package com.sample.ams.service.Interface;


import com.sample.ams.model.BankTransaction;
import com.sample.ams.model.dto.BankTransactionDTO;
import com.sample.ams.model.enumeration.CurrencyType;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;

public interface IBankTransactionService extends IGenericService<BankTransaction, Long, BankTransactionDTO.Info, BankTransactionDTO.Create, BankTransactionDTO.Update> {

    BankTransactionDTO.Info deposit(String to, BigDecimal depositAmount, CurrencyType currencyType);

    BankTransactionDTO.Info withdraw(String from, BigDecimal depositAmount, CurrencyType currencyType);

    BankTransactionDTO.Info transfer(String from, String to, BigDecimal amount, CurrencyType currencyType);

    List<BankTransactionDTO.Info> retrieveTransactionLogs(String startDate, String endDate, String accountNumber, Integer pageNumber, Integer pageCount, Sort.Direction orderType);

    BankTransactionDTO.Info getByTrackingCode(String trackingCode);
}
