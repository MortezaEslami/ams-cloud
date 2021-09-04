package com.sample.ams.controller;


import com.sample.ams.model.dto.AreaDTO;
import com.sample.ams.service.Interface.IAreaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/areas")
public class AreaRestController {

    private final IAreaService addressService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<AreaDTO.Info> get(@PathVariable Long id) {
        return new ResponseEntity<>(addressService.get(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AreaDTO.Info>> list() {
        return new ResponseEntity<>(addressService.list(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AreaDTO.Info> create(@Validated @RequestBody AreaDTO.Create request) {
        return new ResponseEntity<>(addressService.create(request), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AreaDTO.Info> update(@PathVariable Long id, @RequestBody AreaDTO.Update request) {
        return new ResponseEntity<>(addressService.update(id, request), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addressService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/children/{id}")
    public ResponseEntity<List<AreaDTO.Info>> getChildren(@PathVariable Long id) {
        return new ResponseEntity<>(addressService.getChildren(id), HttpStatus.OK);
    }

    @GetMapping(value = "/code/{code}")
    public ResponseEntity<AreaDTO.Info> getByCode(@PathVariable String code) {
        return new ResponseEntity<>(addressService.getByCode(code), HttpStatus.OK);
    }
}
