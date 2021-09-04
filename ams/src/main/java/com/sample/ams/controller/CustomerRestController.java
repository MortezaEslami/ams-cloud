package com.sample.ams.controller;

import com.sample.ams.model.dto.CustomerDTO;
import com.sample.ams.service.Interface.ICustomerService;
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
@RequestMapping(value = "/api/v1/customers")
public class CustomerRestController {

    private final ICustomerService customerService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerDTO.Info> get(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.get(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO.Info>> list() {
        return new ResponseEntity<>(customerService.list(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO.Info> create(@Validated @RequestBody CustomerDTO.Create request) {
        return new ResponseEntity<>(customerService.create(request), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CustomerDTO.Info> update(@PathVariable Long id, @RequestBody CustomerDTO.Update request) {
        return new ResponseEntity<>(customerService.update(id, request), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/listByName")
    public ResponseEntity<List<CustomerDTO.Info>> getByFirstOrLastName(@RequestParam String firstName, @RequestParam String lastName) {
        return new ResponseEntity<>(customerService.getByFirstOrLastName(firstName, lastName), HttpStatus.OK);
    }

    @GetMapping(value = "/mobile/{mobile}")
    public ResponseEntity<List<CustomerDTO.Info>> getByMobile(@PathVariable String mobile) {
        return new ResponseEntity<>(customerService.getByMobile(mobile), HttpStatus.OK);
    }

    @GetMapping(value = "/email/{email}")
    public ResponseEntity<List<CustomerDTO.Info>> getByEmail(@PathVariable String email) {
        return new ResponseEntity<>(customerService.getByEmail(email), HttpStatus.OK);
    }
}
