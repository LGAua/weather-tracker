package com.lga.weathertracker.model.weatherApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lga.weathertracker.util.UnixTimeConverter;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class ForecastApiDto {
    private static final DateTimeFormatter formater = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");

    @JsonProperty("dt")
    @JsonDeserialize(using = UnixTimeConverter.class)
    private LocalDateTime time;

    @JsonProperty("name")
    private String name;

    @JsonProperty("main")
    private Main main;

    @JsonProperty("wind")
    private Wind wind;

    @JsonProperty("weather")
    private List<WeatherMetaInfo> weather;

    public String getTime() {
        return time.format(formater);
    }

    public WeatherMetaInfo getWeather() {
        return weather.stream().findFirst().orElse(null);
    }
}
