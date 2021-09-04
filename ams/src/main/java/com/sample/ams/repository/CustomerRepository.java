package com.sample.ams.repository;

import com.sample.ams.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    List<Customer> findByFirstNameLikeOrLastNameLike(String firstName, String lastName);

    List<Customer> findByMobileLike(String mobile);

    List<Customer> findByEmailLike(String email);
}
