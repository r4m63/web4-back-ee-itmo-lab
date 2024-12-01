package dev.ramil21.web4back.util;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@ApplicationScoped
public class HibernateUtil {
    private SessionFactory sessionFactory;

    @PostConstruct
    public void init() {
        try {
            String configFile = System.getProperty("hibernate.config", "hibernate.cfg.xml");
            sessionFactory = new Configuration().configure(configFile).buildSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании SessionFactory", e);
        }
    }

    @Produces // делает объект SessionFactory доступным для инъекции через CDI
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @PreDestroy
    public void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
