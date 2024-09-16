package com.lga.weathertracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class LocationPositionDto {
    @JsonProperty("name")
    private String name;

    @JsonProperty("lon")
    private double longitude;

    @JsonProperty("lat")
    private double latitude;

    @JsonProperty("country")
    private String country;

}
