package dev.ramil21.web4back.dao.implementations;

import dev.ramil21.web4back.dao.IRefreshTokenDao;
import dev.ramil21.web4back.model.RefreshToken;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.hibernate.SessionFactory;

@Stateless
public class RefreshTokenDaoImpl extends GenericDao<RefreshToken, Long> implements IRefreshTokenDao {

    @Inject
    public RefreshTokenDaoImpl(SessionFactory sessionFactory) {
        super(RefreshToken.class);
        this.sessionFactory = sessionFactory;
    }

}
