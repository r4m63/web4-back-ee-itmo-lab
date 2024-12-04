package dev.ramil21.web4back.dao.implementations;

import dev.ramil21.web4back.dao.IUserDao;
import dev.ramil21.web4back.model.User;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

@Slf4j
@Stateless
public class UserDaoImpl extends BaseDao<User, Long> implements IUserDao {

    @Inject
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(User.class);
        this.sessionFactory = sessionFactory;
    }


    public Optional<User> findByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResultOptional();
        }
    }

    public Optional<User> findByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .uniqueResultOptional();
        }
    }

}
