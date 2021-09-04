package com.sample.ams.controller;

import com.sample.ams.model.dto.BankDTO;
import com.sample.ams.service.Interface.IBankService;
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
@RequestMapping(value = "/api/v1/banks")
public class BankRestController {

    private final IBankService bankService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<BankDTO.Info> get(@PathVariable Long id) {
        return new ResponseEntity<>(bankService.get(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BankDTO.Info>> list() {
        return new ResponseEntity<>(bankService.list(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BankDTO.Info> create(@Validated @RequestBody BankDTO.Create request) {
        return new ResponseEntity<>(bankService.create(request), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BankDTO.Info> update(@PathVariable Long id, @RequestBody BankDTO.Update request) {
        return new ResponseEntity<>(bankService.update(id, request), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bankService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/children/{id}")
    public ResponseEntity<List<BankDTO.Info>> getChildren(@PathVariable Long id) {
        return new ResponseEntity<>(bankService.getChildren(id), HttpStatus.OK);
    }

    @GetMapping(value = "/code/{code}")
    public ResponseEntity<BankDTO.Info> getByCode(@PathVariable String code) {
        return new ResponseEntity<>(bankService.getByCode(code), HttpStatus.OK);
    }

    @GetMapping(value = "/areas/{id}")
    public ResponseEntity<List<BankDTO.Info>> getByArea(@PathVariable Long id) {
        return new ResponseEntity<>(bankService.getByArea(id), HttpStatus.OK);
    }
}
