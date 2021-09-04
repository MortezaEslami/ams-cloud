package com.sample.ams.service.Interface;


import com.sample.ams.model.Area;
import com.sample.ams.model.dto.AreaDTO;

import java.util.List;

public interface IAreaService extends IGenericService<Area, Long, AreaDTO.Info, AreaDTO.Create, AreaDTO.Update> {

    List<AreaDTO.Info> getChildren(Long id);

    AreaDTO.Info getByCode(String code);
}
