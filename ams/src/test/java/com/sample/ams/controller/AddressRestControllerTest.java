package com.sample.ams.controller;

import com.sample.ams.model.dto.AddressDTO;
import com.sample.ams.service.Interface.IAddressService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IAddressService addressService;

    @Test
    public void listAddressTest() throws Exception {
        List<AddressDTO.Info> addressList = new ArrayList<>();

        when(addressService.list()).thenReturn(addressList);

        this.mockMvc.perform(get("/api/areas/list")).
                andDo(print()).andExpect(status().isOk());
    }


    @Test
    public void getAddressTest() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/area/1000")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andReturn();

    }

}
