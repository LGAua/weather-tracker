package com.lga.weathertracker.controller;

import com.lga.weathertracker.entity.User;
import com.lga.weathertracker.model.view.LocationWeatherViewDto;
import com.lga.weathertracker.service.ForecastService;
import com.lga.weathertracker.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final ForecastService forecastService;
    private final SessionService sessionService;

    public SearchController(ForecastService forecastService, SessionService sessionService) {
        this.forecastService = forecastService;
        this.sessionService = sessionService;
    }

    @GetMapping
    public String searchPage(Model model, HttpServletRequest req) {
        User sessionUser = sessionService.getSessionUser(req);
        model.addAttribute("user", sessionUser);

        return "search-result";
    }

    @PostMapping
    public String findLocations(String location, Model model, HttpServletRequest req) {
        User sessionUser = sessionService.getSessionUser(req);
        List<LocationWeatherViewDto> possibleLocations = forecastService.findPossibleLocations(location);

        model.addAttribute("user", sessionUser);
        model.addAttribute("possibleLocations", possibleLocations);

        return "search-result";
    }
}
