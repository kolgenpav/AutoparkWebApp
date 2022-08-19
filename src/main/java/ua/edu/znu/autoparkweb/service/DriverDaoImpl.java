package ua.edu.znu.autoparkweb.service;

import ua.edu.znu.autoparkweb.model.Bus;
import ua.edu.znu.autoparkweb.model.Driver;
import ua.edu.znu.autoparkweb.model.Route;

import javax.persistence.Query;
import java.util.List;

public class DriverDaoImpl extends AutoparkDaoImpl<Driver> {

    public List<Driver> findByNameAndSurname(final String name, final String surname) {
        Query query = entityManager.createQuery("from Driver d where d.name=:name and d.surname=:surname")
                .setParameter("name", name)
                .setParameter("surname", surname);
        return (List<Driver>) query.getResultList();
    }

    public List<Driver> findByAgeGreaterAndEqualThan(final int minimalAge) {
        Query query = entityManager.createQuery("from Driver d where d.age >=:minimalAge")
                .setParameter("minimalAge", minimalAge);
        return (List<Driver>) query.getResultList();
    }

    public List<Driver> findByBus(final Bus bus) {
        Query query = entityManager.createQuery("from Driver d where :bus member of d.buses")
                .setParameter("bus", bus);
        return (List<Driver>) query.getResultList();
    }
}
