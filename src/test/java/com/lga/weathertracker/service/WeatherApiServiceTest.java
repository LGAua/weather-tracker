package com.lga.weathertracker.service;

import com.lga.weathertracker.model.weatherApi.ForecastApiDto;
import com.lga.weathertracker.model.weatherApi.LocationPositionApiDto;
import com.lga.weathertracker.model.weatherApi.WeatherApiResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@SpringBootTest
class WeatherApiServiceTest {

    private static final String VALENCIA = "Valencia";

    private final WeatherApiService weatherApiService;

    @Autowired
    public WeatherApiServiceTest(WeatherApiService weatherApiService) {
        this.weatherApiService = weatherApiService;
    }

    @Test
    void checkCoordinatesNotNullAndInstanceOfDouble() throws URISyntaxException, IOException, InterruptedException {

        List<LocationPositionApiDto> locationPositionByName = weatherApiService.getLocationPositionByName(VALENCIA);
        System.out.println(locationPositionByName);

    }

    @Test
    void checkForecastByPositionResponse() throws URISyntaxException, IOException, InterruptedException {

        List<ForecastApiDto> forecast = weatherApiService.getForecastByPosition(43.1, 23.1);
        System.out.println(forecast);

    }

    @Test
    void checkCurrentWeatherByPositionResponse() throws URISyntaxException, IOException, InterruptedException {

        ForecastApiDto currentWeather = weatherApiService.getCurrentWeatherByPosition(43.1, 23.1);
        System.out.println(currentWeather);

    }

    @Test
    void checkForecastAndCityNameByPositionResponse() throws URISyntaxException, IOException, InterruptedException {

        WeatherApiResponseDto currentWeather = weatherApiService.getForecastAndCityNameByPosition(43.1, 23.1);
        System.out.println(currentWeather);

    }


}