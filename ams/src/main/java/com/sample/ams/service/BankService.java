package com.sample.ams.service;

import com.sample.ams.model.Bank;
import com.sample.ams.model.dto.BankDTO;
import com.sample.ams.repository.BankRepository;
import com.sample.ams.service.Interface.IBankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BankService extends GenericService<Bank, Long, BankDTO.Info, BankDTO.Create, BankDTO.Update> implements IBankService {

    private final BankRepository bankRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BankDTO.Info> getChildren(Long id) {
        List<Bank> bankList = bankRepository.findByParentId(id);
        return bankList.stream().map(item ->
                modelMapper.map(item, BankDTO.Info.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BankDTO.Info getByCode(String code) {
        Bank bank = bankRepository.findByCode(code);
        return modelMapper.map(bank, BankDTO.Info.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankDTO.Info> getByArea(Long id) {
        List<Bank> bankList = bankRepository.findByAreaId(id);
        return bankList.stream().map(item ->
                modelMapper.map(item, BankDTO.Info.class))
                .collect(Collectors.toList());
    }
}

