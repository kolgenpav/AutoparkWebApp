package ua.edu.znu.autoparkweb.conroller.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import ua.edu.znu.autoparkweb.model.Bus;
import ua.edu.znu.autoparkweb.service.BusDaoImpl;
import ua.edu.znu.autoparkweb.service.DriverDaoImpl;
import ua.edu.znu.autoparkweb.service.RouteDaoImpl;
import ua.edu.znu.autoparkweb.service.UserDaoImpl;

import java.util.List;

public class AutoparkContextListener implements ServletContextListener {
    private UserDaoImpl userDao;
    private BusDaoImpl busDao;
    private DriverDaoImpl driverDao;
    private RouteDaoImpl routeDao;
    private List<Bus> buses;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);
        userDao = new UserDaoImpl();
        busDao = new BusDaoImpl();
        driverDao = new DriverDaoImpl();
        routeDao = new RouteDaoImpl();
        buses = busDao.findAll();
        ServletContext app = sce.getServletContext();
        app.setAttribute("userDao", userDao);
        app.setAttribute("busDao", busDao);
        app.setAttribute("driverDao", driverDao);
        app.setAttribute("routeDao", routeDao);
        app.setAttribute("buses", buses);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
        userDao = null;
        busDao = null;
        driverDao = null;
        routeDao = null;
    }
}
