package com.carrent.controller;

import com.carrent.domain.Car;
import com.carrent.dto.CarDto;
import com.carrent.dto.CreateCarDto;
import com.carrent.dto.GetCarDto;
import com.carrent.dto.UpdateCarAndEquipment;
import com.carrent.exception.AdditionalEquipmentNotFoundException;
import com.carrent.exception.CarNotFoundException;
import com.carrent.mapper.CarMapper;
import com.carrent.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1")

@Transactional
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private CarMapper carMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/cars")
    public List<CarDto> getCars() {
        return carMapper.getCarsDtoList(carService.getCarsList());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cars/{carId}")
    public GetCarDto getCar(@PathVariable Long carId) throws CarNotFoundException {
        return carMapper.mapToGetCarDto(carService.getCar(carId).orElseThrow(CarNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cars", consumes = APPLICATION_JSON_VALUE)
    public Car createCar(@RequestBody CreateCarDto createCarDto) {
        return carService.saveCar(carMapper.mapToCar(createCarDto));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/cars/{carId}/status/{status}", consumes = APPLICATION_JSON_VALUE)
    public GetCarDto updateCarStatus(@PathVariable Long carId, @PathVariable boolean status) throws CarNotFoundException {
        return carMapper.mapToGetCarDto(carService.updateStatus(carId, status));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/car")
    public void updateCarEquipment(@RequestBody UpdateCarAndEquipment updateCarAndEquipment) throws CarNotFoundException, AdditionalEquipmentNotFoundException {
        carService.addEquipmentToCar(updateCarAndEquipment);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/car/equipment")
    public GetCarDto deleteEquipmentFromCar(@RequestBody UpdateCarAndEquipment updateCarAndEquipment) throws CarNotFoundException, AdditionalEquipmentNotFoundException {
        return carMapper.mapToGetCarDto(carService.deleteEquipmentFromCar(updateCarAndEquipment));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/cars/{carId}")
    public void deleteCar(@PathVariable Long carId) {
        carService.deleteCar(carId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/cars/{carId}")
    public GetCarDto returningTheCar (@PathVariable Long carId) throws CarNotFoundException, AdditionalEquipmentNotFoundException {
        return carMapper.mapToGetCarDto(carService.returnCarToRental(carId));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/cars/{carId}/prize/{prize}")
    public GetCarDto updatePrize(@PathVariable Long carId, @PathVariable double prize) throws CarNotFoundException {
        return carMapper.mapToGetCarDto(carService.updatePrize(carId,prize));
    }
}