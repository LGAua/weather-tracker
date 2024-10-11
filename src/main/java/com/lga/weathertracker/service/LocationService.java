package com.lga.weathertracker.service;


import com.lga.weathertracker.entity.Location;
import com.lga.weathertracker.repository.LocationRepository;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public void deleteLocation(Location location, Integer userId) {
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        locationRepository.deleteByCoordinatesAndUserId(longitude, latitude, userId);
//        locationRepository.deleteByLocationNameAndUserId(location.getName(), userId);
    }
}
