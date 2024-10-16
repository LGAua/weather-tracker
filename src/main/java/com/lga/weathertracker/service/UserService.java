package com.lga.weathertracker.service;

import com.lga.weathertracker.entity.Location;
import com.lga.weathertracker.entity.User;
import com.lga.weathertracker.repository.BaseRepository;
import com.password4j.Password;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.password4j.Password.check;

@Slf4j
@Service
public class UserService {

    private final BaseRepository<Integer, User> userRepository;
    private final BaseRepository<Integer, Location> locationRepository;

    public UserService(BaseRepository<Integer, User> userRepository, BaseRepository<Integer, Location> locationRepository) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
    }

    public boolean verifyExistence(String login) {
        Optional<User> optionalUser = userRepository.findByName(login);
        return optionalUser.isPresent();
    }

    public boolean verifyCredentials(User user) {
        Optional<User> optionalUser = userRepository.findByName(user.getLogin());
        return optionalUser.map(u -> check(user.getPassword(), u.getPassword()).withBcrypt())
                .orElse(false);
    }

    public Location saveLocationForUser(Location location, User user) {
        location.setUser(user);
        user.addNewLocation(location);
        log.info("Saving location: {} for user: {}", location.getName(), user.getLogin());
        return locationRepository.save(location);
    }

    public void saveNewUser(User user) {
        log.info("Saving user: {}", user.getLogin());
        userRepository.save(user);
    }
}
