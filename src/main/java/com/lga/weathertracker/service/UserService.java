package com.lga.weathertracker.service;

import com.lga.weathertracker.entity.User;
import com.lga.weathertracker.repository.BaseRepository;
import com.lga.weathertracker.repository.UsersRepository;

import java.util.Optional;

public class UserService {

    private final BaseRepository<Integer, User> userRepository = new UsersRepository();

    public boolean verifyExistence(String login){
        Optional<User> optionalUser = userRepository.findByName(login);
        return optionalUser.isPresent();
    }

    public boolean verifyCredentials(String login, String password){
        Optional<User> optionalUser = userRepository.findByName(login);
        return optionalUser.map(user -> user.getPassword().equals(password))
                .orElse(false);
    }

    public void saveNewUser(User user) {
        userRepository.save(user);
    }

    public User findUserByLogin(String login) {
        return userRepository.findByName(login).orElse(null);
    }
}
