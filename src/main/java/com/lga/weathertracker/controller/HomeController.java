package com.lga.weathertracker.controller;

import com.lga.weathertracker.entity.Location;
import com.lga.weathertracker.entity.User;
import com.lga.weathertracker.model.view.LocationWeatherViewDto;
import com.lga.weathertracker.service.ForecastService;
import com.lga.weathertracker.service.LocationService;
import com.lga.weathertracker.service.SessionService;
import com.lga.weathertracker.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final ForecastService forecastService;
    private final SessionService sessionService;
    private final UserService userService;
    private final LocationService locationService;

    public HomeController(ForecastService forecastService, SessionService sessionService, UserService userService, LocationService locationService) {
        this.forecastService = forecastService;
        this.sessionService = sessionService;
        this.userService = userService;
        this.locationService = locationService;
    }

    @GetMapping
    public String homePage(Model model, HttpServletRequest req) {
        User sessionUser = sessionService.getSessionUser(req);
        List<LocationWeatherViewDto> locationsCurrentWeather = forecastService.getForecastForLocations(sessionUser.getLocations());

        model.addAttribute("user", sessionUser);
        model.addAttribute("locations", locationsCurrentWeather);
        return "home";
    }

    @PostMapping
    public String saveLocation(@ModelAttribute Location location, HttpServletRequest req) {
        sessionService.findUserByRequestSessionId(req)
                .ifPresent(user -> userService.saveLocationForUser(location, user));

        return "redirect:/home";
    }

    @PostMapping("/delete")
    public String deleteLocation(@RequestParam("lat") double lat,
                                 @RequestParam("lon") double lon,
                                 HttpServletRequest req) {
        sessionService.findUserByRequestSessionId(req)
                .ifPresent(user -> locationService.deleteLocation(lat, lon, user.getId()));
        return "redirect:/home";
    }
}
