package com.carrent.controller;


import com.carrent.openweather.OpenWeatherClient;
import com.carrent.weather.domain.OpenWeatherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/weather")
public class WeatherController {

    @Autowired
    private OpenWeatherClient openWeatherClient;

    @RequestMapping(method = RequestMethod.GET, value = "/getWeather")
    public OpenWeatherDto getWeather() {
        return openWeatherClient.getWeatherFor5Days();
    }
}