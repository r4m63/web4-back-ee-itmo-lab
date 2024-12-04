package dev.ramil21.web4back.dao;

import java.util.List;

public interface IBaseDao<T, ID> {
    T findById(ID id);

    List<T> findAll();

    void save(T entity);

    void update(T entity);

    void delete(T entity);
}
