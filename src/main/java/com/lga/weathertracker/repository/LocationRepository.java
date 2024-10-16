package com.lga.weathertracker.repository;

import com.lga.weathertracker.entity.Location;
import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaDelete;
import org.hibernate.query.criteria.JpaRoot;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class LocationRepository extends BaseRepository<Integer, Location> {

    public LocationRepository() {
        super(Location.class);
    }

    @Override
    public Optional<Location> findByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Location location = session.createQuery("select l from Location l where l.name = :name", Location.class)
                    .setParameter("name", name)
                    .getSingleResultOrNull();
            session.getTransaction().commit();
            return Optional.ofNullable(location);
        }
    }

    public void deleteByCoordinatesAndUserId(double lat, double lon, Integer userId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
            JpaCriteriaDelete<Location> delete = cb.createCriteriaDelete(Location.class);
            JpaRoot<Location> table = delete.from(Location.class);

            delete.where(
                    cb.equal(table.get("latitude"), lat),
                    cb.equal(table.get("longitude"), lon),
                    cb.equal(table.get("user").get("id"), userId)
            );

            session.createMutationQuery(delete).executeUpdate();
            session.getTransaction().commit();
        }
    }
}
