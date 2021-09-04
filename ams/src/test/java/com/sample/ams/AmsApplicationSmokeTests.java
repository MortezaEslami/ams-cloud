package com.sample.ams;

import com.sample.ams.controller.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class AmsApplicationSmokeTests {

    @Autowired
    private AddressRestController addressRestController;

    @Autowired
    private BankRestController bankRestController;

    @Autowired
    private AreaRestController areaRestController;

    @Autowired
    private CustomerRestController customerRestController;

    @Autowired
    private BankTransactionRestController bankTransactionRestController;

    @Autowired
    private BankAccountRestController bankAccountRestController;


    @Test
    void contextLoads() throws Exception {
        assertThat(addressRestController).isNotNull();
        assertThat(areaRestController).isNotNull();
        assertThat(customerRestController).isNotNull();
        assertThat(bankRestController).isNotNull();
        assertThat(bankAccountRestController).isNotNull();
        assertThat(bankTransactionRestController).isNotNull();
    }

}
