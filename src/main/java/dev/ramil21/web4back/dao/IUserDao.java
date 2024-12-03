package dev.ramil21.web4back.dao;

import dev.ramil21.web4back.model.User;

import java.util.Optional;

public interface IUserDao extends IAGenericDao<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
