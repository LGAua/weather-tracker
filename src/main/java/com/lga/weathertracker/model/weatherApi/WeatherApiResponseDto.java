package com.lga.weathertracker.model.weatherApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class WeatherApiResponseDto {

    @JsonProperty("list")
    private List<ForecastApiDto> forecastDtoList;

    @JsonProperty("city")
    private City city;
}
