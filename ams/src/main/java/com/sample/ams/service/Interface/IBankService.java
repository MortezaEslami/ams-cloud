package com.sample.ams.service.Interface;

import com.sample.ams.model.Bank;
import com.sample.ams.model.dto.BankDTO;

import java.util.List;

public interface IBankService extends IGenericService<Bank, Long, BankDTO.Info, BankDTO.Create, BankDTO.Update> {

    List<BankDTO.Info> getChildren(Long id);

    BankDTO.Info getByCode(String code);

    List<BankDTO.Info> getByArea(Long id);
}
