package com.lga.weathertracker.model.weatherApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Main {

    @JsonProperty("temp")
    private double temp;

    @JsonProperty("temp_min")
    private double tempMin;

    @JsonProperty("temp_max")
    private double tempMax;

    @JsonProperty("feels_like")
    private double feelsLike;

    @JsonProperty("pressure")
    private double pressure;
}
