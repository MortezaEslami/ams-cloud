package com.sample.ams.controller;

import com.sample.ams.service.Interface.IBankTransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BankTransactionRestController.class)
public class BankTransactionsRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IBankTransactionService bankTransactionService;

    @Test
    public void checkLogsService() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/bankTransactions/logs?endDate=2021-12-10&orderType=ASC&pageCount=5&pageNumber=1&startDate=2022-11-03")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("[]"))
                .andReturn();
    }


}
