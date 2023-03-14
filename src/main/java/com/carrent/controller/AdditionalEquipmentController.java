package com.carrent.controller;


import com.carrent.domain.AdditionalEquipment;
import com.carrent.dto.CreateEquipmentDto;
import com.carrent.dto.EquipmentDto;
import com.carrent.dto.GetEquipmentDto;
import com.carrent.exception.AdditionalEquipmentNotFoundException;
import com.carrent.mapper.AdditionalEquipmentMapper;
import com.carrent.service.AdditionalEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1")
@Transactional
public class AdditionalEquipmentController {

    @Autowired
    private AdditionalEquipmentService additionalEquipmentService;

    @Autowired
    private AdditionalEquipmentMapper additionalEquipmentMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/equipments")
    public List<GetEquipmentDto> getEquipmentList() {
        return additionalEquipmentMapper.mapToEquipmentDtoList(additionalEquipmentService.getEquipmentList());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/equipments/{equipmentId}")
    public EquipmentDto getEquipmentDto(@PathVariable Long equipmentId) throws AdditionalEquipmentNotFoundException {
        return additionalEquipmentMapper.mapToEquipmentDto(additionalEquipmentService.getEquipment(equipmentId).orElseThrow(AdditionalEquipmentNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/equipments")
    public AdditionalEquipment createEquipment(@RequestBody CreateEquipmentDto createEquipmentDto) {
        return additionalEquipmentService.saveEquipment(additionalEquipmentMapper.mapToAdditionalEquipment(createEquipmentDto));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/equipments/{equipmentId}")
    public void deleteEquipment(@PathVariable Long equipmentId) {
        additionalEquipmentService.deleteEquipment(equipmentId);
    }
}