package com.lga.weathertracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lga.weathertracker.model.ForecastDto;
import lombok.Getter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class WeatherApiResponseDto {

    @JsonProperty("list")
    private List<ForecastDto> forecastDtoList;

}
