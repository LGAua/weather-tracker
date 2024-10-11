package com.lga.weathertracker.controller;

import com.lga.weathertracker.model.view.LocationWeatherViewDto;
import com.lga.weathertracker.model.weatherApi.LocationPositionApiDto;
import com.lga.weathertracker.service.ForecastService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {
    private final ForecastService forecastService;

    public SearchController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @PostMapping
    public String findLocations(String location, Model model){
        List<LocationWeatherViewDto> possibleLocations = forecastService.findPossibleLocations(location);
        model.addAttribute("possibleLocations", possibleLocations);
        return "search-result";
    }
}
