package com.lga.weathertracker.repository;

import com.lga.weathertracker.exception.DataBaseQueryException;
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
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            E e = session.find(clazz, entityId);
            session.getTransaction().commit();
            return Optional.ofNullable(e);
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            throw new DataBaseQueryException("Exception in %s, unable to find by id".formatted(this.clazz.getName()));
        }
    }

    public List<E> findAll() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            HibernateCriteriaBuilder cb = session.getCriteriaBuilder();

            JpaCriteriaQuery<E> query = cb.createQuery(clazz);
            JpaRoot<E> from = query.from(clazz);
            query.select(from);

            List<E> list = session.createQuery(query).list();
            session.getTransaction().commit();
            return list;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new DataBaseQueryException("Exception in %s, unable to find all entities".formatted(this.clazz.getName()));
        }
    }

    public E save(E entity) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            System.out.println(entity);
            throw new DataBaseQueryException("Exception in %s, unable to save".formatted(this.clazz.getName()));
        }
    }

    public void update(E entity) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.merge(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new DataBaseQueryException("Exception in %s, unable to find by id".formatted(this.clazz.getName()));
        }
    }

    public void delete(E entity) {
        Session session = sessionFactory.openSession();
        try (session) {
            session.beginTransaction();
            session.remove(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new DataBaseQueryException("Exception in %s, unable to delete".formatted(this.clazz.getName()));
        }
    }
}
