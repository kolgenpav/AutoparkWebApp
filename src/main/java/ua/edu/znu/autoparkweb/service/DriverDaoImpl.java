package ua.edu.znu.autoparkweb.service;

import ua.edu.znu.autoparkweb.model.Bus;
import ua.edu.znu.autoparkweb.model.Driver;

import javax.persistence.TypedQuery;
import java.util.List;

public class DriverDaoImpl extends AutoparkDaoImpl<Driver> {

    public List<Driver> findByNameAndSurname(final String name, final String surname) {
        TypedQuery<Driver> query = entityManager
                .createQuery("from Driver d where d.name=:name and d.surname=:surname", Driver.class)
                .setParameter("name", name)
                .setParameter("surname", surname);
        return getResultList(query);
    }

    public List<Driver> findByAgeGreaterAndEqualThan(final int minimalAge) {
        TypedQuery<Driver> query = entityManager
                .createQuery("from Driver d where d.age >=:minimalAge", Driver.class)
                .setParameter("minimalAge", minimalAge);
        return getResultList(query);
    }

    public List<Driver> findByBus(final Bus bus) {
        TypedQuery<Driver> query = entityManager
                .createQuery("from Driver d where :bus member of d.buses", Driver.class)
                .setParameter("bus", bus);
        return getResultList(query);
    }
}
