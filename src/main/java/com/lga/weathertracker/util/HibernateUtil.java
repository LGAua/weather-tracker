package com.lga.weathertracker.util;

import com.lga.weathertracker.entity.Location;
import com.lga.weathertracker.entity.Session;
import com.lga.weathertracker.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        Configuration configuration = new Configuration();

        configuration.setProperty("hibernate.connection.url", System.getenv("SPRING_DATASOURCE_URL"));
        configuration.setProperty("hibernate.connection.username", System.getenv("SPRING_DATASOURCE_USERNAME"));
        configuration.setProperty("hibernate.connection.password", System.getenv("SPRING_DATASOURCE_PASSWORD"));
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");

        configuration.setProperty("show_sql", true);
        configuration.setProperty("format_sql", true);

        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Location.class);
        configuration.addAnnotatedClass(Session.class);

        sessionFactory = configuration.buildSessionFactory();
    }

    public static SessionFactory buildSessionFactory(){
        return sessionFactory;
    }
}
