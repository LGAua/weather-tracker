package com.lga.weathertracker.controller;

import com.lga.weathertracker.entity.Location;
import com.lga.weathertracker.entity.User;
import com.lga.weathertracker.model.view.LocationWeatherViewDto;
import com.lga.weathertracker.model.weatherApi.ForecastApiDto;
import com.lga.weathertracker.service.ForecastService;
import com.lga.weathertracker.service.LocationService;
import com.lga.weathertracker.service.SessionService;
import com.lga.weathertracker.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        Optional<String> sessionId = Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals("sessionId"))
                .map(Cookie::getValue)
                .findFirst();

        if (sessionId.isPresent() && sessionService.isSessionIdValid(sessionId.get())) {
            User sessionUser = sessionService.getSessionUser(sessionId.get());
            model.addAttribute("user", sessionUser);

            List<LocationWeatherViewDto> locationsCurrentWeather = forecastService.getForecastForLocations(sessionUser.getLocations());
            model.addAttribute("locations", locationsCurrentWeather);
        }

        return "home";
    }


    @PostMapping
    public String searchForLocation(@ModelAttribute Location location, HttpServletRequest req) {
        Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals("sessionId"))
                .map(cookie -> sessionService.getSessionUser(cookie.getValue()))
                .findFirst()
                .ifPresent(user -> userService.saveLocationForUser(location, user));

        return "redirect:/home";
    }

    @PostMapping("/delete")
    public String deleteLocation(@RequestParam("locationName") String locationName,
                                 @RequestParam("lat") double lat,
                                 @RequestParam("lon") double lon,
                                 HttpServletRequest req) {
        Location location = Location.builder()
                .name(locationName)
                .latitude(lat)
                .longitude(lon)
                .build();
        Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals("sessionId"))
                .map(cookie -> sessionService.getSessionUser(cookie.getValue()))
                .findFirst()
                .ifPresent(user ->
                        locationService.deleteLocation(location, user.getId()));

        return "redirect:/home";
    }
}
