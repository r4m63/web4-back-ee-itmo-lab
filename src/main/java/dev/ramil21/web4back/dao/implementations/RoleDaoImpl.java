package dev.ramil21.web4back.dao.implementations;

import dev.ramil21.web4back.model.Role;
import jakarta.ejb.Stateless;
import dev.ramil21.web4back.dao.IRoleDao;
import jakarta.inject.Inject;
import org.hibernate.SessionFactory;

@Stateless
public class RoleDaoImpl extends GenericDao<Role, Long> implements IRoleDao {

    @Inject
    public RoleDaoImpl(SessionFactory sessionFactory) {
        super(Role.class);
        this.sessionFactory = sessionFactory;
    }

}
