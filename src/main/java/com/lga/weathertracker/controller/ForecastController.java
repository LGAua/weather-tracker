package com.lga.weathertracker.controller;

import com.lga.weathertracker.model.weatherApi.ForecastApiDto;
import com.lga.weathertracker.model.weatherApi.WeatherApiResponseDto;
import com.lga.weathertracker.service.ForecastService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/forecast")
public class ForecastController {

    private final ForecastService forecastService;

    public ForecastController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @GetMapping
    public String forecastPage(){
        return "forecast";
    }


    @PostMapping
    public String getForecastForPosition(@RequestParam double lat,
                                         @RequestParam double lon,
                                         @RequestParam String city,
                                         Model model){
        WeatherApiResponseDto forecastForPosition = forecastService.getForecastForPosition(lat, lon);
        model.addAttribute("city", city);
        model.addAttribute("forecastList", forecastForPosition.getForecastDtoList());
        return "forecast";
    }
}
