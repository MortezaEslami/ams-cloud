package com.sample.ams.service;

import com.sample.ams.model.Customer;
import com.sample.ams.model.dto.CustomerDTO;
import com.sample.ams.repository.CustomerRepository;
import com.sample.ams.service.Interface.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomerService extends GenericService<Customer, Long, CustomerDTO.Info, CustomerDTO.Create, CustomerDTO.Update> implements ICustomerService {

    private final CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDTO.Info> getByFirstOrLastName(String firstName, String lastName) {
        List<Customer> customerList = customerRepository.findByFirstNameLikeOrLastNameLike(firstName, lastName);
        return customerList.stream().map(item ->
                modelMapper.map(item, CustomerDTO.Info.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDTO.Info> getByMobile(String mobile) {
        List<Customer> customerList = customerRepository.findByMobileLike(mobile);
        return customerList.stream().map(item ->
                modelMapper.map(item, CustomerDTO.Info.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDTO.Info> getByEmail(String email) {
        List<Customer> customerList = customerRepository.findByEmailLike(email);
        return customerList.stream().map(item ->
                modelMapper.map(item, CustomerDTO.Info.class))
                .collect(Collectors.toList());
    }
}

