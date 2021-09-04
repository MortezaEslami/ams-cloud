package com.sample.ams.controller;

import com.sample.ams.model.dto.AreaDTO;
import com.sample.ams.service.Interface.IAreaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AreaRestController.class)
public class AreaRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAreaService areaService;

    @Test
    public void listWithoutItem() throws Exception {
        when(areaService.list()).thenReturn(
                Arrays.asList()
        );

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/areas/list")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("[]"))
                .andReturn();
    }


    @Test
    public void getAreaTest() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/area/1000")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void listArea() throws Exception {
        when(areaService.list()).thenReturn(
                Arrays.asList(new AreaDTO.Info(1L, new Date(), new Date()))
        );

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/areas/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
    }


}
