package com.sample.ams.repository;

import com.sample.ams.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long>, JpaSpecificationExecutor<Bank> {

    List<Bank> findByParentId(Long id);

    Bank findByCode(String code);

    List<Bank> findByAreaId(Long id);
}
