package com.sample.ams.service;

import com.sample.ams.model.Area;
import com.sample.ams.model.dto.AreaDTO;
import com.sample.ams.repository.AreaRepository;
import com.sample.ams.service.Interface.IAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AreaService extends GenericService<Area, Long, AreaDTO.Info, AreaDTO.Create, AreaDTO.Update> implements IAreaService {

    private final AreaRepository areaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AreaDTO.Info> getChildren(Long id) {
        List<Area> areaList = areaRepository.findByParentId(id);
        return areaList.stream().map(item ->
                modelMapper.map(item, AreaDTO.Info.class))
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public AreaDTO.Info getByCode(String code) {
        Area area = areaRepository.findByCode(code);
        return modelMapper.map(area, AreaDTO.Info.class);
    }


}

