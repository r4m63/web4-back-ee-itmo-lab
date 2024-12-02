package dev.ramil21.web4back.dao;

import dev.ramil21.web4back.dao.interfaces.IUserDao;
import dev.ramil21.web4back.model.User;
import org.hibernate.SessionFactory;

public class UserDao extends GenericDao<User, Long> implements IUserDao {
    public UserDao(SessionFactory sessionFactory) {
        super(User.class);
    }
}
