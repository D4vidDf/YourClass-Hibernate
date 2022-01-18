package com.d4viddf.TablasDAO;

import org.hibernate.Session;

import java.util.List;

public interface DAO<T> {
    public void persist(T entity, Session session);

    public void update(T entity, Session session);

    public T get(int id, Session session);

    public void delete(T entity, Session session);

    public List<T> getAll(Session session);

    public void deleteAll(Session sesion);
}
