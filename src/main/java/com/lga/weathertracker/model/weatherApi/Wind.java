package com.lga.weathertracker.model.weatherApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Wind {

    @JsonProperty("speed")
    private double speed;

    @JsonProperty("deg")
    private double deg;
}
