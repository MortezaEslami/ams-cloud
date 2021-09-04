package com.sample.ams.service;

import com.github.javafaker.Faker;
import com.sample.ams.model.Area;
import com.sample.ams.model.dto.AreaDTO;
import com.sample.ams.model.enumeration.AreaType;
import com.sample.ams.repository.AreaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


public class AreaServiceTest {

    @Mock
    AreaRepository areaRepository;

    @InjectMocks
    AreaService areaService;

    @Mock
    protected ModelMapper modelMapper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void listTest() {
        List<Area> list = new ArrayList<Area>();
        Area area1 = new Area(1L, null, null, "Country1", "co_1", AreaType.Country, 0, "comment");
        /*area1.setCreatedDate(new Date());*/
        Area area2 = new Area(2L, null, null, "Country2", "co_2", AreaType.Country, 0, "comment");
        /*area2.setCreatedDate(new Date());*/
        list.add(area1);
        list.add(area2);
        when(areaRepository.findAll()).thenReturn(list);
        when(modelMapper.map(area1, AreaDTO.Info.class)).thenReturn(new AreaDTO.Info(1L, new Date(), new Date()));
        List<AreaDTO.Info> areaList = areaService.list();
        assertThat(areaList.size()).isNotZero().isEqualTo(2);
    }

    @Test
    public void getTest() {
        Area area = new Area(1L, null, null, "Country1", "co_1", AreaType.Country, 0, "comment");
        Optional<Area> areaOptional = Optional.of(area);
        when(areaRepository.findById(1L)).thenReturn(areaOptional);
        when(modelMapper.map(area, AreaDTO.Info.class)).thenReturn(new AreaDTO.Info(1L, new Date(), new Date()));
        AreaDTO.Info areaDTO = areaService.get(1L);
        assertThat(areaDTO.getId()).isEqualTo(1L);
    }

    @Test
    void createTest() {
        Area area = new Area(1L, null, null, "Country1", "co_1", AreaType.Country, 0, "comment");
        AreaDTO.Create areaDTO = new AreaDTO.Create();
        areaDTO.setAreaType(AreaType.Country);
        areaDTO.setCode("Cu-Isf");
        areaDTO.setName("Isfahan");
        AreaDTO.Info areaDTO1 = new AreaDTO.Info();
        when(areaRepository.save(area)).thenReturn(area);
        when(modelMapper.map(areaDTO, Area.class)).thenReturn(area);
        when(modelMapper.map(area, AreaDTO.Info.class)).thenReturn(areaDTO1);
        when(areaRepository.saveAndFlush(area)).thenReturn(area);
        assertThat(areaService.create(areaDTO)).isNotNull();
    }


    @Test
    void creatArea(){


        Faker faker = new Faker();
        Area area =  new Area();
        area.setCode(faker.address().countryCode());
        area.setName(faker.address().cityName());


    }
}
