package dev.ramil21.web4back.dao.implementations;

import dev.ramil21.web4back.dao.IPointDao;
import dev.ramil21.web4back.model.Point;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Stateless
public class PointDaoImpl extends GenericDao<Point, Long> implements IPointDao {

    @Inject
    public PointDaoImpl(SessionFactory sessionFactory) {
        super(Point.class);
        this.sessionFactory = sessionFactory;
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
