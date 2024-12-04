package dev.ramil21.web4back.dao.implementations;

import dev.ramil21.web4back.dao.IBaseDao;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

@Slf4j
@Stateless
public abstract class BaseDao<T, ID> implements IBaseDao<T, ID> {
    private final Class<T> entityClass;

    @Inject
    protected SessionFactory sessionFactory;

    protected BaseDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public  T findById(ID id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(entityClass, id);
        }
    }

    public List<T> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM " + entityClass.getSimpleName(), entityClass).list();
        }
    }

    /**
     * Методы Hibernate:
     * (Depricated)
     * persist -> save
     * merge -> update или saveOrUpdate
     * remove -> delete
     * find -> get
     */
    public void save(T entity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            log.error("SAVE GENERICDAO EXCEPTION", e);
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void update(T entity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            log.error("UPDATE GENERICDAO EXCEPTION", e);
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void delete(T entity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            log.error("DELETE GENERICDAO EXCEPTION", e);
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}

