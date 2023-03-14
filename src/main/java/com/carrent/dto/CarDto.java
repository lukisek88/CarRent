package com.carrent.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
    private Long id;
    private String carClass;
    private String typeOfCar;
    private String producer;
    private String model;
    private LocalDate dayOfProduction;
    private double pricePerDay;
    private String color;
    private int numberOfSeats;
    private boolean availability;
}