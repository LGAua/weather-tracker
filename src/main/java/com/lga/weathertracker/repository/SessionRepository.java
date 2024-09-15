package com.lga.weathertracker.repository;

import com.lga.weathertracker.entity.Session;

import java.util.Optional;

public class SessionRepository extends BaseRepository<String, Session> {

    public SessionRepository() {
        super(Session.class);
    }

    @Override
    public Optional<Session> findByName(String name) {
        try (org.hibernate.Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Session userSession = session.createQuery("select s from Session s " +
                            "join User u on u.id = s.user.id " +
                            "where u.login = :login", Session.class)
                    .setParameter("login", name)
                    .getSingleResultOrNull();
            session.getTransaction().commit();
            return Optional.ofNullable(userSession);
        }
    }
}
