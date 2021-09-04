package com.sample.ams.controller;

import com.sample.ams.model.dto.BankAccountDTO;
import com.sample.ams.model.enumeration.AccountStatus;
import com.sample.ams.model.enumeration.AccountType;
import com.sample.ams.service.Interface.IBankAccountService;
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
@RequestMapping(value = "/api/v1/bank-accounts")
public class BankAccountRestController {

    private final IBankAccountService bankAccountService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<BankAccountDTO.Info> get(@PathVariable Long id) {
        return new ResponseEntity<>(bankAccountService.get(id), HttpStatus.OK);
    }

    @GetMapping(value = "/accountNumber/{accountNumber}")
    public ResponseEntity<BankAccountDTO.Info> getByAccountNumber(@PathVariable String accountNumber) {
        return new ResponseEntity<>(bankAccountService.getByAccountNumber(accountNumber), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BankAccountDTO.Info>> list() {
        return new ResponseEntity<>(bankAccountService.list(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BankAccountDTO.Info> create(@Validated @RequestBody BankAccountDTO.Create request) {
        return new ResponseEntity<>(bankAccountService.create(request), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BankAccountDTO.Info> update(@PathVariable Long id, @RequestBody BankAccountDTO.Update request) {
        return new ResponseEntity<>(bankAccountService.update(id, request), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bankAccountService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/customer/{id}")
    public ResponseEntity<List<BankAccountDTO.Info>> getByCustomer(@PathVariable Long id) {
        return new ResponseEntity<>(bankAccountService.getByCustomer(id), HttpStatus.OK);
    }


    @GetMapping(value = "/accountNumberContaining/{accountNumber}")
    public ResponseEntity<List<BankAccountDTO.Info>> getByAccountNumberContaining(@PathVariable String accountNumber) {
        return new ResponseEntity<>(bankAccountService.getByAccountNumberContaining(accountNumber), HttpStatus.OK);
    }


    @GetMapping(value = "/cardNumberContaining/{cardNumber}")
    public ResponseEntity<List<BankAccountDTO.Info>> getByCardNumberContaining(@PathVariable String cardNumber) {
        return new ResponseEntity<>(bankAccountService.getByCardNumberContaining(cardNumber), HttpStatus.OK);
    }


    @GetMapping(value = "/accountType/{accountType}")
    public ResponseEntity<List<BankAccountDTO.Info>> getByAccountType(@PathVariable AccountType accountType) {
        return new ResponseEntity<>(bankAccountService.getByAccountType(accountType), HttpStatus.OK);
    }


    @GetMapping(value = "/accountStatus/{accountStatus}")
    public ResponseEntity<List<BankAccountDTO.Info>> getByAccountStatus(@PathVariable AccountStatus accountStatus) {
        return new ResponseEntity<>(bankAccountService.getByAccountStatus(accountStatus), HttpStatus.OK);
    }


    @PatchMapping(value = "/block/accountNumber/{accountNumber}")
    public ResponseEntity<BankAccountDTO.Info> blockBankAccount(@PathVariable String accountNumber) {
        return new ResponseEntity<>(bankAccountService.changeStatus(accountNumber , AccountStatus.Blocked), HttpStatus.OK);
    }

    @PatchMapping(value = "/close/accountNumber/{accountNumber}")
    public ResponseEntity<BankAccountDTO.Info> closeBankAccount(@PathVariable String accountNumber) {
        return new ResponseEntity<>(bankAccountService.changeStatus(accountNumber, AccountStatus.Close), HttpStatus.OK);
    }

    @PatchMapping(value = "/unblock/accountNumber/{accountNumber}")
    public ResponseEntity<BankAccountDTO.Info> unblockBankAccount(@PathVariable String accountNumber) {
        return new ResponseEntity<>(bankAccountService.changeStatus(accountNumber, AccountStatus.Open), HttpStatus.OK);
    }

}
