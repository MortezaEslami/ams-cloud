package com.sample.ams.service;

import com.sample.ams.model.Area;
import com.sample.ams.model.Bank;
import com.sample.ams.model.dto.AreaDTO;
import com.sample.ams.model.dto.BankDTO;
import com.sample.ams.repository.BankRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


public class BankServiceTest {

    @Mock
    BankRepository bankRepository;

    @InjectMocks
    BankService bankService;

    @Mock
    protected ModelMapper modelMapper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getTest() {
        Bank bank = new Bank(1L, null, null, "New Bank", "Ne_BK",new Area() , 1L , 0 ,"comment");
        Optional<Bank> bankOptional = Optional.of(bank);
        when(bankRepository.findById(1L)).thenReturn(bankOptional);
        when(modelMapper.map(bank, BankDTO.Info.class)).thenReturn(new BankDTO.Info(1L, new Date(), new Date() , new AreaDTO.Info()));
        BankDTO.Info bankDTO = bankService.get(1L);
        assertThat(bankDTO.getId()).isEqualTo(1L);
    }

    @Test
    void createTest() {
        Bank bank = new Bank(1L, null, null, "New Bank", "Ne_BK",new Area() , 1L , 0 ,"comment");
        BankDTO.Create bankDTO = new BankDTO.Create();
        bankDTO.setCode("Cu-Isf");
        bankDTO.setName("Isfahan");
        BankDTO.Info bankDTO1 = new BankDTO.Info();
        when(bankRepository.save(bank)).thenReturn(bank);
        when(modelMapper.map(bankDTO, Bank.class)).thenReturn(bank);
        when(modelMapper.map(bank, BankDTO.Info.class)).thenReturn(bankDTO1);
        when(bankRepository.saveAndFlush(bank)).thenReturn(bank);
        assertThat(bankService.create(bankDTO)).isNotNull();
    }
}
