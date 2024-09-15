package com.lga.weathertracker.repository;

import com.lga.weathertracker.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<K extends Serializable, E> {
    protected static final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

    private final Class<E> clazz;

    public BaseRepository(Class<E> clazz) {
        this.clazz = clazz;
    }

    public abstract Optional<E> findByName(String name);

    public Optional<E> findById(K entityId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            E e = session.find(clazz, entityId);
            session.getTransaction().commit();
            return Optional.ofNullable(e);
        }
    }

    public List<E> findAll(){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            HibernateCriteriaBuilder cb = session.getCriteriaBuilder();

            JpaCriteriaQuery<E> query = cb.createQuery(clazz);
            JpaRoot<E> from = query.from(clazz);
            query.select(from);

            List<E> list = session.createQuery(query).list();
            session.getTransaction().commit();
            return list;
        }
    }

    public E save(E e) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(e);
            session.getTransaction().commit();
            return e;
        }
    }

    public void update(E e) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(e);
            session.getTransaction().commit();
        }
    }

    public void delete(E e) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(e);
            session.getTransaction().commit();
        }
    }
}
