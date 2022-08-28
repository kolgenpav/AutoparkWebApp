package ua.edu.znu.autoparkweb.service;

import ua.edu.znu.autoparkweb.model.Bus;
import ua.edu.znu.autoparkweb.model.Driver;

import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

public class DriverDaoImpl extends AutoparkDaoImpl<Driver> {

    public DriverDaoImpl() {
        setClazz(Driver.class);
    }

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

    public void addBusToDriver(Long driverId, Bus bus){

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Driver driver = findById(driverId);
            Set<Bus> buses = driver.getBuses();
            buses.add(bus);
            update(driver);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public void removeBusFromDriver(Long driverId, Bus bus){

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Driver driver = findById(driverId);
            //TODO can not get collection
            Set<Bus> buses = driver.getBuses();
            buses.remove(bus);
//            update(driver);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }
}
