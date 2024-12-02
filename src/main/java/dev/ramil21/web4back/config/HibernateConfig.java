package dev.ramil21.web4back.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@ApplicationScoped
public class HibernateConfig {

    private SessionFactory sessionFactory;

    @PostConstruct
    public void init() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании SessionFactory", e);
        }
    }

    @Produces // делает объект SessionFactory доступным для инъекции через CDI
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @PreDestroy
    public void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}