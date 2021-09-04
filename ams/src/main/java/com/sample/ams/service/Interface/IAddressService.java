package com.sample.ams.service.Interface;


import com.sample.ams.model.Address;
import com.sample.ams.model.dto.AddressDTO;

import java.util.List;

public interface IAddressService extends IGenericService<Address, Long, AddressDTO.Info, AddressDTO.Create, AddressDTO.Update> {

    List<AddressDTO.Info> getCustomerAddress(Long id);
}
