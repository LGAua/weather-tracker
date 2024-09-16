package com.lga.weathertracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lga.weathertracker.model.weatherApi.Main;
import com.lga.weathertracker.model.weatherApi.WeatherMetaInfo;
import com.lga.weathertracker.model.weatherApi.Wind;
import com.lga.weathertracker.util.UnixTimeConverter;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class ForecastDto {

    @JsonProperty("dt")
    @JsonDeserialize(using = UnixTimeConverter.class)
    private LocalDateTime time;

    @JsonProperty("main")
    private Main main;

    @JsonProperty("wind")
    private Wind wind;

    @JsonProperty("weather")
    private List<WeatherMetaInfo> weather;
}
