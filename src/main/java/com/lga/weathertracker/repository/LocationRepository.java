package com.lga.weathertracker.repository;

import com.lga.weathertracker.entity.Location;
import com.lga.weathertracker.entity.User;
import org.hibernate.Session;

import java.util.Optional;

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
}
