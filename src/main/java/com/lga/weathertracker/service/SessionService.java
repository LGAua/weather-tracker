package com.lga.weathertracker.service;

import com.lga.weathertracker.entity.Session;
import com.lga.weathertracker.entity.User;
import com.lga.weathertracker.repository.BaseRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class SessionService {

    private final static int SESSION_LIFETIME_IN_WEEKS = 1;

    private final BaseRepository<String, Session> sessionRepository;
    private final BaseRepository<Integer, User> userRepository;

    public SessionService(BaseRepository<String, Session> sessionRepository, BaseRepository<Integer, User> userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }


    public UUID saveNewSession(User user) {
        Optional<User> optionalUser = userRepository.findByName(user.getLogin());
        if (optionalUser.isPresent()) {
            Session session = Session.builder()
                    .id(UUID.randomUUID().toString())
                    .user(optionalUser.get())
                    .expiresAt(LocalDateTime.now().plusWeeks(SESSION_LIFETIME_IN_WEEKS)).build();

            sessionRepository.save(session);
            log.info("Assign sessionId: {} for user: {}",session.getId(), user.getLogin());
            return UUID.fromString(session.getId());
        }
        return null;
    }

    public void invalidateSession(String name) {
        Optional<Session> session = sessionRepository.findById(name);
        session.ifPresent(sessionRepository::delete);
    }


    public boolean isSessionValid(HttpServletRequest req) {
        Optional<String> sessionId = Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals("sessionId"))
                .map(Cookie::getValue)
                .findFirst();

        return sessionId.isPresent() && isSessionIdValid(sessionId.get());
    }

    public User getSessionUser(HttpServletRequest req) {
        return Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals("sessionId"))
                .map(Cookie::getValue)
                .map(this::findUserBySessionId)
                .findFirst()
                .orElse(null);
    }

    public Optional<User> findUserByRequestSessionId(HttpServletRequest req) {
        Optional<Session> session = Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals("sessionId"))
                .map(cookie -> sessionRepository.findById(cookie.getValue()))
                .findFirst()
                .orElse(Optional.empty());

        return session.map(Session::getUser);
    }

    private User findUserBySessionId(String sessionId) {
        return sessionRepository.findById(sessionId)
                .map(Session::getUser)
                .orElse(null);
    }

    private boolean isSessionIdValid(String value) {
        Optional<Session> sessionOptional = sessionRepository.findById(value);
        return sessionOptional.map(session -> session.getExpiresAt()
                        .isAfter(LocalDateTime.now()))
                .orElse(false);
    }
}
