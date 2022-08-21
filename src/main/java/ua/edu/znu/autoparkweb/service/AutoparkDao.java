package ua.edu.znu.autoparkweb.service;

import ua.edu.znu.autoparkweb.model.User;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Interface with universal methods that working with entities' instances of Autopark database..
 */
public interface AutoparkDao<T> {

    void setClazz(Class<T> clazzToSet);

    T findById(final long id);

    List<T> findAll();

    T getSingleResult(TypedQuery<T> query);

    List<T> getResultList(TypedQuery<T> query);

    void create(final T entity);

    void update(final T entity);

    void delete(final T entity);

    void deleteById(final long entityId);
}
