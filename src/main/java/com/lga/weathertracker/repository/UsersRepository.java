package com.lga.weathertracker.repository;

import com.lga.weathertracker.entity.User;
import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
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
