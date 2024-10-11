package com.lga.weathertracker.service;

import com.lga.weathertracker.model.weatherApi.ForecastApiDto;
import com.lga.weathertracker.model.weatherApi.LocationPositionApiDto;
import com.lga.weathertracker.model.weatherApi.WeatherApiResponseDto;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class WeatherApiService {

    private static final String APP_ID = "c3873790b38e322b853fddbdc91601ae";

    private static final String BASE_URL = "https://api.openweathermap.org/";
    private static final String GEO_URL = "geo/1.0/direct?q=%s&limit=5&appid=%s";

    private static final String CURRENT_WEATHER_URL = "data/2.5/weather?lat=%f&lon=%f&units=%s&appid=%s";
    private static final String HOURLY_FORECAST_URL = "data/2.5/forecast?lat=%f&lon=%f&units=%s&appid=%s";
    private static final String MEASUREMENT_UNITS = "metric";
    @Getter
    private final String PICTURE_URL = "https://openweathermap.org/img/wn/%s.png";
    private final WebClient client;


    public WeatherApiService(WebClient.Builder client) {
        this.client = client.baseUrl(BASE_URL).build();
    }

    public List<LocationPositionApiDto> getLocationPositionByName(String location) {
        return client.get()
                .uri(GEO_URL.formatted(location, APP_ID))
                .retrieve()
                .bodyToFlux(LocationPositionApiDto.class)
                .collectList()
                .block();
    }

    public List<ForecastApiDto> getForecastByPosition(double lat, double lon) {
        return client.get()
                .uri(HOURLY_FORECAST_URL.formatted(lat, lon, MEASUREMENT_UNITS, APP_ID))
                .retrieve()
                .bodyToMono(Map.class)
                .map(map -> (List<ForecastApiDto>) map.get("list"))
                .block();
    }

    public ForecastApiDto getCurrentWeatherByPosition(double lat, double lon) {
        return client.get()
                .uri(CURRENT_WEATHER_URL.formatted(lat, lon, MEASUREMENT_UNITS, APP_ID))
                .retrieve()
                .bodyToMono(ForecastApiDto.class)
                .block();
    }

//    public WeatherApiResponseDto getForecastAndCityNameByPosition(double lat, double lon) {
//        return client.get()
//                .uri(HOURLY_FORECAST_URL.formatted(lat, lon, MEASUREMENT_UNITS, APP_ID))
//                .retrieve()
//                .bodyToMono(WeatherApiResponseDto.class)
//                .block();
//    }
}
