package com.sample.ams.repository;

import com.sample.ams.model.BankTransaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long>, JpaSpecificationExecutor<BankTransaction> {

    @Query(value = " select b  from BankTransaction b where b.transactionStatus=1 and (b.originAccountNumber =:accountNumber or b.destinationAccountNumber =:accountNumber) and b.transactionTime >= :startDate AND b.transactionTime <= :endDate ")
    List<BankTransaction> getAllTransactionBetweenDatesByAccountNumber(@Param("startDate") Date startDate, @Param("endDate") Date endDate ,@Param("accountNumber") String accountNumber ,  Pageable pageable);

    @Query(value = " select b  from BankTransaction b where b.transactionStatus=1 and b.transactionTime >= :startDate AND b.transactionTime <= :endDate ")
    List<BankTransaction> getAllTransactionBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate ,  Pageable pageable);

    Optional<BankTransaction> findByTrackingCode(String trackingCode);
}
