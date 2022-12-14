package ua.edu.znu.autoparkweb.service;

import ua.edu.znu.autoparkweb.model.Bus;
import ua.edu.znu.autoparkweb.model.Driver;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

/**
 * Specific Driver methods.
 */
public class DriverDaoImpl extends AutoparkDaoImpl<Driver> {

    public DriverDaoImpl() {
        setClazz(Driver.class);
    }

    public List<Driver> findByBus(final Bus bus) {
        EntityManager entityManager = getEntityManager();
        TypedQuery<Driver> query = entityManager
                .createQuery("from Driver d where :bus member of d.buses", Driver.class)
                .setParameter("bus", bus);
        return getResultList(query);
    }

    public void removeBusFromDriver(Long driverId, Bus bus) {
        try {
            Driver driver = findById(driverId);
            Set<Bus> buses = driver.getBuses();
            buses.remove(bus);
            update(driver);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
