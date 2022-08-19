package ua.edu.znu.autoparkweb.service;

import java.util.List;

/**
 * Interface with universal methods that working with entities' instances of Autopark database..
 */
public interface AutoparkDao<T> {

    void setClazz(Class<T> clazzToSet);

    T findOne(final long id);

    List<T> findAll();

    void create(final T entity);

    T update(final T entity);

    void delete(final T entity);

    void deleteById(final long entityId);
}
