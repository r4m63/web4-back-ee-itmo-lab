package dev.ramil21.web4back.dao.implementations;

import dev.ramil21.web4back.dao.IRefreshTokenDao;
import dev.ramil21.web4back.model.RefreshToken;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;

@Slf4j
@Stateless
public class RefreshTokenDaoImpl extends BaseDao<RefreshToken, Long> implements IRefreshTokenDao {

    @Inject
    public RefreshTokenDaoImpl(SessionFactory sessionFactory) {
        super(RefreshToken.class);
        this.sessionFactory = sessionFactory;
    }

}
