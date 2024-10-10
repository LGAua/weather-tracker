package com.lga.weathertracker.service;

import com.lga.weathertracker.entity.User;
import com.lga.weathertracker.repository.BaseRepository;
import com.lga.weathertracker.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final BaseRepository<Integer, User> userRepository;

    public UserService(BaseRepository<Integer, User> userRepository) {
        this.userRepository = userRepository;
    }

    public boolean verifyExistence(String login) {
        Optional<User> optionalUser = userRepository.findByName(login);
        return optionalUser.isPresent();
    }

    public boolean verifyCredentials(User user) {
        Optional<User> optionalUser = userRepository.findByName(user.getLogin());
        return optionalUser.map(u -> u.getPassword().equals(user.getPassword()))
                .orElse(false);
    }

    public void saveNewUser(User user) {
        userRepository.save(user);
    }

    public User findUserByLogin(String login) {
        return userRepository.findByName(login).orElse(null);
    }
}
