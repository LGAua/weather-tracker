package com.lga.weathertracker.service;

import com.lga.weathertracker.repository.LocationRepository;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public void deleteLocation(double lat, double lon, Integer userId) {
        locationRepository.deleteByCoordinatesAndUserId(lat, lon, userId);
    }
}
