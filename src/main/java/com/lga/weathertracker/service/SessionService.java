package com.lga.weathertracker.service;

import com.lga.weathertracker.entity.Session;
import com.lga.weathertracker.entity.User;
import com.lga.weathertracker.repository.BaseRepository;
import com.lga.weathertracker.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class SessionService {

    private final static int SESSION_LIFETIME_IN_WEEKS = 1;

    private final BaseRepository<String, Session> sessionRepository;
    private final BaseRepository<Integer, User> userRepository;

    public SessionService(BaseRepository<String, Session> sessionRepository, BaseRepository<Integer, User> userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }


    public boolean isSessionIdValid(String value) {
        Optional<Session> sessionOptional = sessionRepository.findById(value);
        return sessionOptional.map(session -> session.getExpiresAt()
                        .isAfter(LocalDateTime.now()))
                .orElse(false);
    }

    public User getSessionUser(String value) {
        Optional<Session> sessionOptional = sessionRepository.findById(value);
        return sessionOptional.map(Session::getUser)
                .orElse(null);
    }

    public UUID saveNewSession(User user) {
        Optional<User> optionalUser = userRepository.findByName(user.getLogin());
        if (optionalUser.isPresent()) {
            Session session = Session.builder()
                    .id(UUID.randomUUID().toString())
                    .user(optionalUser.get())
                    .expiresAt(LocalDateTime.now().plusWeeks(SESSION_LIFETIME_IN_WEEKS)).build();

            sessionRepository.save(session);
            return UUID.fromString(session.getId());
        }
        return null;
    }

    public void invalidateSession(String name) {
        Optional<Session> session = sessionRepository.findById(name);
        session.ifPresent(sessionRepository::delete);
    }
}
