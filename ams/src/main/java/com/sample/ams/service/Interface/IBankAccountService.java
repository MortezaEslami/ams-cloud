package com.sample.ams.service.Interface;


import com.sample.ams.model.BankAccount;
import com.sample.ams.model.dto.BankAccountDTO;
import com.sample.ams.model.enumeration.AccountStatus;
import com.sample.ams.model.enumeration.AccountType;

import java.math.BigDecimal;
import java.util.List;

public interface IBankAccountService extends IGenericService<BankAccount, Long, BankAccountDTO.Info, BankAccountDTO.Create, BankAccountDTO.Update> {

    BankAccountDTO.Info getByAccountNumber(String accountNumber);

    void deposit(String destinationAccountNumber, BigDecimal depositAmount);

    void withdraw(String originAccountNumber, BigDecimal withdrawAmount);

    List<BankAccountDTO.Info> getByCustomer(Long id);

    List<BankAccountDTO.Info> getByAccountNumberContaining(String accountNumber);

    List<BankAccountDTO.Info> getByCardNumberContaining(String cardNumber);

    List<BankAccountDTO.Info> getByAccountType(AccountType accountType);

    List<BankAccountDTO.Info> getByAccountStatus(AccountStatus accountStatus);

    BankAccountDTO.Info changeStatus(String accountNumber, AccountStatus accountStatus);
}
