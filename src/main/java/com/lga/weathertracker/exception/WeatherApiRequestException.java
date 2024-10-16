package com.lga.weathertracker.exception;

public class WeatherApiRequestException extends RuntimeException{
    public WeatherApiRequestException(String message) {
        super(message);
    }
}
