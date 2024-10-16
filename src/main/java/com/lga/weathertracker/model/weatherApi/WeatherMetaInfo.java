package com.lga.weathertracker.model.weatherApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class WeatherMetaInfo {
    private final static String ICON_URL = "https://openweathermap.org/img/wn/%s.png";

    @JsonProperty("main")
    private String main;

    @JsonProperty("description")
    private String description;

    @JsonProperty("icon")
    private String iconID;

    public String getIconUrl() {
        return ICON_URL.formatted(iconID);
    }
}
