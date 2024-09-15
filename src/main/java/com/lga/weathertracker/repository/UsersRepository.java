package com.lga.weathertracker.repository;

import com.lga.weathertracker.entity.User;
import org.hibernate.Session;

import java.util.Optional;

public class UsersRepository extends BaseRepository<Integer, User>{

    public UsersRepository() {
        super(User.class);
    }

    @Override
    public Optional<User> findByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.createQuery("select u from User u where u.login = :login", User.class)
                    .setParameter("login", name)
                    .getSingleResultOrNull();
            session.getTransaction().commit();
            return Optional.ofNullable(user);
        }
    }
}
