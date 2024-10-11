package com.lga.weathertracker.model.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lga.weathertracker.model.weatherApi.ForecastApiDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationWeatherViewDto {

    private Integer id;

    private String city;

    private String country;

    private ForecastApiDto forecast;

    private double latitude;

    private double longitude;
}
