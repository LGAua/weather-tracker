package com.lga.weathertracker.service;

import com.lga.weathertracker.entity.Session;
import com.lga.weathertracker.entity.User;
import com.lga.weathertracker.repository.BaseRepository;
import com.lga.weathertracker.repository.SessionRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class SessionService {

    private final static int SESSION_LIFETIME_IN_WEEKS = 1;
    private final BaseRepository<String, Session> sessionRepository = new SessionRepository();


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

    public void saveNewSession(UUID uuid, User user) {
        Session session = Session.builder()
                .id(uuid.toString())
                .user(user)
                .expiresAt(LocalDateTime.now().plusWeeks(SESSION_LIFETIME_IN_WEEKS)).build();

        sessionRepository.save(session);
    }

    public void invalidateSession(String name) {
        Optional<Session> session = sessionRepository.findById(name);
        session.ifPresent(sessionRepository::delete);
    }
}
