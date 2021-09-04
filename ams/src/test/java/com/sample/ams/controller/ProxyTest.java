package com.sample.ams.controller;

import com.sample.ams.proxy.CurrencyExchangeRateProxy;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Value;
import static org.assertj.core.api.Assertions.assertThat;



public class ProxyTest {

    @InjectMocks
    private CurrencyExchangeRateProxy proxy;

    @Value("${api.key}")
    private String key;

    @Test
    public void testFeinProxy() {
        /*Gson g = new Gson();
        ResultApiCall resultApiCall = new ResultApiCall();
        int i = 0;
        float exchangeRate = 0;
        while (i++ < 10) {
            resultApiCall = g.fromJson(proxy.currencyExchangeRate(key, "USD"), ResultApiCall.class);
            if (resultApiCall.isSuccess()) {
                exchangeRate = Float.parseFloat(resultApiCall.getRates().getUSD());
                break;
            }
        }
        assertThat(resultApiCall.getRates()).isNotNull();*/
    }

}
