package com.sample.ams.controller;

import com.sample.ams.model.dto.AddressDTO;
import com.sample.ams.service.Interface.IAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/addresses")
public class AddressRestController {

    private final IAddressService addressService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<AddressDTO.Info> get(@PathVariable Long id) {
        return new ResponseEntity<>(addressService.get(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AddressDTO.Info>> list() {
        return new ResponseEntity<>(addressService.list(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AddressDTO.Info> create(@Validated @RequestBody AddressDTO.Create request) {
        return new ResponseEntity<>(addressService.create(request), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AddressDTO.Info> update(@PathVariable Long id, @RequestBody AddressDTO.Update request) {
        return new ResponseEntity<>(addressService.update(id, request), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addressService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/customers/{id}")
    public ResponseEntity<List<AddressDTO.Info>> getCustomerAddress(@PathVariable Long id) {
        return new ResponseEntity<>(addressService.getCustomerAddress(id), HttpStatus.OK);
    }
}
