package com.sample.ams.service;

import com.sample.ams.model.Address;
import com.sample.ams.model.dto.AddressDTO;
import com.sample.ams.repository.AddressRepository;
import com.sample.ams.service.Interface.IAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AddressService extends GenericService<Address, Long, AddressDTO.Info, AddressDTO.Create, AddressDTO.Update> implements IAddressService {

    private final AddressRepository addressRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AddressDTO.Info> getCustomerAddress(Long id) {
        List<Address> addressList = addressRepository.findByCustomerId(id);
        return addressList.stream().map(item ->
                modelMapper.map(item, AddressDTO.Info.class))
                .collect(Collectors.toList());
    }
}

