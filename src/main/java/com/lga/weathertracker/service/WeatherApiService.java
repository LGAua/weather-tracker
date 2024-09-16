package com.lga.weathertracker.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.lga.weathertracker.model.ForecastDto;
import com.lga.weathertracker.model.LocationPositionDto;
import com.lga.weathertracker.model.WeatherApiResponseDto;
import lombok.Getter;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

public class WeatherApiService {

    private static final String APP_ID = "c3873790b38e322b853fddbdc91601ae";
    private static final String BASE_URL = "https://api.openweathermap.org/";

    private static final String POSITION_URL = "geo/1.0/direct?q=%s&appid=%s";
    private static final String CURRENT_WEATHER_URL = "data/2.5/weather?lat=%f&lon=%f&units=%s&appid=%s";
    private static final String HOURLY_FORECAST_URL = "data/2.5/forecast?lat=%f&lon=%f&units=%s&appid=%s";
    private static final String MEASUREMENT_UNITS = "metric";
    @Getter

    private final String PICTURE_URL = "https://openweathermap.org/img/wn/%s.png";

    private final JsonMapper jsonMapper = new JsonMapper();


    public LocationPositionDto getLocationPositionByName(String location) throws URISyntaxException, IOException, InterruptedException {
        URI requestUri = new URI(BASE_URL + POSITION_URL.formatted(location, APP_ID));

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest
                .newBuilder()
                .uri(requestUri)
                .GET()
                .build();

        HttpResponse<String> response = client.send(req, BodyHandlers.ofString());

        List<LocationPositionDto> locations = jsonMapper.readValue(response.body(),
                new TypeReference<>() {
                });
        return locations.stream().findAny().orElse(null);


    }

    public  WeatherApiResponseDto  getForecastByPosition(double lat, double lon) throws URISyntaxException, IOException, InterruptedException {
        URI requestUri = new URI(BASE_URL + HOURLY_FORECAST_URL.formatted(lat, lon, MEASUREMENT_UNITS,  APP_ID));

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest
                .newBuilder()
                .uri(requestUri)
                .GET()
                .build();

        HttpResponse<String> response = client.send(req, BodyHandlers.ofString());

        WeatherApiResponseDto forecastDto = jsonMapper.readValue(response.body(),WeatherApiResponseDto.class);

        return forecastDto;

    }

    public ForecastDto getCurrentWeatherByPosition(double lat, double lon) throws URISyntaxException, IOException, InterruptedException {
        URI requestUri = new URI(BASE_URL + CURRENT_WEATHER_URL.formatted(lat, lon, MEASUREMENT_UNITS,  APP_ID));

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest
                .newBuilder()
                .uri(requestUri)
                .GET()
                .build();

        HttpResponse<String> response = client.send(req, BodyHandlers.ofString());

        ForecastDto currentWeather = jsonMapper.readValue(response.body(),ForecastDto.class);

        return currentWeather;

    }



}
