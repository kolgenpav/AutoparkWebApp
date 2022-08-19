package ua.edu.znu.autoparkweb.service;

import ua.edu.znu.autoparkweb.model.Bus;
import ua.edu.znu.autoparkweb.model.Driver;
import ua.edu.znu.autoparkweb.model.Route;

import javax.persistence.Query;
import java.util.List;

public class BusDaoImpl extends AutoparkDaoImpl<Bus> {

    public Bus findByNumber(final String number) {
        Query query = entityManager.createQuery("from Bus b where b.number=:number")
                .setParameter("number", number);
        return (Bus) query.getSingleResult();
    }

    public List<Bus> findByRoute(final Route route) {
        Query query = entityManager.createQuery("from Bus b where b.route=:route")
                .setParameter("route", route);
        return (List<Bus>) query.getResultList();
    }

    public List<Driver> findBusDrivers(final Driver driver) {
        Query query = entityManager.createQuery("from Bus b where b.driver=:driver")
                .setParameter("driver", driver);
        return (List<Driver>) query.getResultList();
    }
}
