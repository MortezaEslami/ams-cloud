package com.sample.ams.proxy;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "currency-exchange-rate", url = "http://data.fixer.io/api")
public interface CurrencyExchangeRateProxy {

    @GetMapping("/latest")
    String currencyExchangeRate(@RequestParam(value = "access_key") String key ,
                                                        @RequestParam(value = "symbols") String symbols);

}