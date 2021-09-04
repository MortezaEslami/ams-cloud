package com.sample.ams.controller;

import com.sample.ams.model.dto.BankTransactionDTO;
import com.sample.ams.model.enumeration.CurrencyType;
import com.sample.ams.service.Interface.IBankTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/bank-transactions")
public class BankTransactionRestController {

    private final IBankTransactionService bankTransactionService;

    @GetMapping(value = "/from/{from}/amount/{amount}/currencyType/{type}")
    public ResponseEntity<BankTransactionDTO.Info> withdraw(@PathVariable String from,
                                                            @PathVariable BigDecimal amount,
                                                            @PathVariable CurrencyType type) {
        return new ResponseEntity<>(bankTransactionService.withdraw(from, amount, type),
                HttpStatus.OK);
    }

    @GetMapping(value = "/to/{to}/amount/{amount}/currencyType/{type}")
    public ResponseEntity<BankTransactionDTO.Info> deposit(@PathVariable String to,
                                                           @PathVariable BigDecimal amount,
                                                           @PathVariable CurrencyType type) {
        return new ResponseEntity<>(bankTransactionService.deposit(to, amount, type),
                HttpStatus.OK);
    }

    @GetMapping(value = "/from/{from}/to/{to}/amount/{amount}/currencyType/{type}")
    public ResponseEntity<BankTransactionDTO.Info> transfer(@PathVariable String from,
                                                            @PathVariable String to,
                                                            @PathVariable BigDecimal amount,
                                                            @PathVariable CurrencyType type) {
        return new ResponseEntity<>(bankTransactionService.transfer(from, to, amount, type)
                , HttpStatus.OK);
    }

    @GetMapping(value = "/logs")
    public ResponseEntity<List<BankTransactionDTO.Info>> logs(@RequestParam String startDate,
                                                              @RequestParam String endDate,
                                                              @RequestParam(required = false) String accountNumber,
                                                              @RequestParam  Integer pageNumber,
                                                              @RequestParam  Integer pageCount,
                                                              @RequestParam Sort.Direction orderType) {

        List<BankTransactionDTO.Info> bankTransactionDTOList = bankTransactionService
                .retrieveTransactionLogs(startDate, endDate, accountNumber,
                        pageNumber, pageCount, orderType);
        return new ResponseEntity<>(bankTransactionDTOList, HttpStatus.OK);
    }


    @GetMapping(value = "/trackingCode/{trackingCode}")
    public ResponseEntity<BankTransactionDTO.Info> getByTrackingCode(@PathVariable String trackingCode) {
        return new ResponseEntity<>(bankTransactionService.getByTrackingCode(trackingCode), HttpStatus.OK);
    }

}
