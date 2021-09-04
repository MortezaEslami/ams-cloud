package com.sample.ams.repository;

import com.sample.ams.model.BankAccount;
import com.sample.ams.model.enumeration.AccountStatus;
import com.sample.ams.model.enumeration.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long>, JpaSpecificationExecutor<BankAccount> {

    Optional<BankAccount> findByAccountNumber(String accountNumber);

    @Modifying
    @Query(value = "update tbl_bank_account t set t.n_balance = t.n_balance + :depositAmount where t.c_account_number=:destinationAccountNumber", nativeQuery = true)
    void deposit(String destinationAccountNumber, BigDecimal depositAmount);

    @Modifying
    @Query(value = "update tbl_bank_account t set t.n_balance = t.n_balance - :withdrawAmount where t.c_account_number=:originAccountNumber", nativeQuery = true)
    void withdraw(String originAccountNumber, BigDecimal withdrawAmount);

    List<BankAccount> findByCustomerId(Long id);

    List<BankAccount> findByAccountNumberContaining(String accountNumber);

    List<BankAccount> findByCardNumberContaining(String cardNumber);

    List<BankAccount> findByAccountType(AccountType accountType);

    List<BankAccount> findByAccountStatus(AccountStatus accountStatus);
}
