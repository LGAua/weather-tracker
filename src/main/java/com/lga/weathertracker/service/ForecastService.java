package com.lga.weathertracker.service;

import com.lga.weathertracker.entity.Location;
import com.lga.weathertracker.model.view.LocationWeatherViewDto;
import com.lga.weathertracker.model.weatherApi.ForecastApiDto;
import com.lga.weathertracker.model.weatherApi.LocationPositionApiDto;
import com.lga.weathertracker.model.weatherApi.WeatherApiResponseDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ForecastService {

    private final WeatherApiService weatherApiService;
    private final LocationService locationService;

    public ForecastService(WeatherApiService weatherApiService, LocationService locationService) {
        this.weatherApiService = weatherApiService;
        this.locationService = locationService;
    }

    public List<LocationWeatherViewDto> getForecastForLocations(List<Location> locations) {
        if (locations != null && !locations.isEmpty()) {
            List<ForecastApiDto> forecastDtoList = new ArrayList<>();
            for (Location location : locations) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                ForecastApiDto currentWeather = weatherApiService.getCurrentWeatherByPosition(lat, lon);
                forecastDtoList.add(currentWeather);
            }
            return buildViewDtoFromLocationEntity(locations, forecastDtoList);
        }
        return new ArrayList<>();
    }

    public List<LocationWeatherViewDto> findPossibleLocations(String location) {
        List<LocationPositionApiDto> locationsApi = weatherApiService.getLocationPositionByName(location);

        List<ForecastApiDto> forecastForLocations = new ArrayList<>();
        for (LocationPositionApiDto apiDto : locationsApi) {
            forecastForLocations.add(
                    weatherApiService.getCurrentWeatherByPosition(apiDto.getLatitude(), apiDto.getLongitude())
            );
        }

        return buildViewDtoFromLocationApiDto(locationsApi, forecastForLocations);
    }

    public WeatherApiResponseDto getForecastForPosition(double lat, double lon){
        return weatherApiService.getForecastByPosition(lat, lon);
    }


    private List<LocationWeatherViewDto> buildViewDtoFromLocationApiDto(List<LocationPositionApiDto> locationApi,
                                                                        List<ForecastApiDto> forecastForLocations) {
        List<LocationWeatherViewDto> viewDtos = new ArrayList<>();
        for (int i = 0; i < locationApi.size(); i++) {
            LocationPositionApiDto apiDto = locationApi.get(i);
            viewDtos.add(
                    LocationWeatherViewDto.builder()
                            .city(apiDto.getName())
                            .country(apiDto.getCountry())
                            .longitude(apiDto.getLongitude())
                            .latitude(apiDto.getLatitude())
                            .forecast(forecastForLocations.get(i))
                            .build()
            );
        }
        return viewDtos;
    }

    private List<LocationWeatherViewDto> buildViewDtoFromLocationEntity(List<Location> locations,
                                                                List<ForecastApiDto> forecastForLocations) {
        List<LocationWeatherViewDto> viewDtos = new ArrayList<>();
        for (int i = 0; i < locations.size(); i++) {
            Location location = locations.get(i);
            viewDtos.add(
                    LocationWeatherViewDto.builder()
                            .city(location.getName())
                            .longitude(location.getLongitude())
                            .latitude(location.getLatitude())
                            .forecast(forecastForLocations.get(i))
                            .build()
            );
        }
        return viewDtos;
    }
}
