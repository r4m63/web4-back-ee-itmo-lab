package dev.ramil21.web4back.dao;

import dev.ramil21.web4back.dao.interfaces.IRoleDao;
import dev.ramil21.web4back.model.Role;
import org.hibernate.SessionFactory;

public class RoleDao extends GenericDao<Role, Long> implements IRoleDao {

    public RoleDao(SessionFactory sessionFactory) {
        super(Role.class);
    }
}
