package dev.ramil21.web4back.dao.implementations;

import jakarta.ejb.Stateless;
import dev.ramil21.web4back.dao.IUserDao;
import dev.ramil21.web4back.model.User;
import jakarta.inject.Inject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

@Stateless
public class UserDaoImpl extends GenericDao<User, Long> implements IUserDao {

    @Inject
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(User.class);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResultOptional();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .uniqueResultOptional();
        }
    }

}
