package com.sample.ams.service.Interface;


import com.sample.ams.model.Customer;
import com.sample.ams.model.dto.CustomerDTO;

import java.util.List;

public interface ICustomerService extends IGenericService<Customer, Long, CustomerDTO.Info, CustomerDTO.Create, CustomerDTO.Update> {

    List<CustomerDTO.Info> getByFirstOrLastName(String firstName, String lastName);

    List<CustomerDTO.Info> getByMobile(String mobile);

    List<CustomerDTO.Info> getByEmail(String email);
}
