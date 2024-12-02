package dev.ramil21.web4back.dao;

import dev.ramil21.web4back.dao.interfaces.IPointDao;
import dev.ramil21.web4back.model.Point;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public class PointDao extends GenericDao<Point, Long> implements IPointDao {

    public PointDao(SessionFactory sessionFactory) {
        super(Point.class);
    }

    public void exampleSaveMethod(Point point) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(point);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
