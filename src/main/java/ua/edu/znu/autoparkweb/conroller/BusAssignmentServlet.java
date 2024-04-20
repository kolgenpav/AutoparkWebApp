package ua.edu.znu.autoparkweb.conroller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.edu.znu.autoparkweb.model.Bus;
import ua.edu.znu.autoparkweb.model.Driver;
import ua.edu.znu.autoparkweb.model.Route;
import ua.edu.znu.autoparkweb.service.BusDaoImpl;
import ua.edu.znu.autoparkweb.service.DriverDaoImpl;
import ua.edu.znu.autoparkweb.service.RouteDaoImpl;

import java.io.IOException;
import java.util.List;

/**
 * Assigns the route and drivers to the selected bus.
 */
@WebServlet("/BusAssignmentServlet")
public class BusAssignmentServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {
        /* busId passed as parameter from home.html and forwarded as request attribute
         * from BusAddServlet*/
        String action = request.getParameter("action") == null
                ? (String) request.getAttribute("action") : request.getParameter("action");
        Long busId = request.getParameter("busId") == null
                ? (Long) request.getAttribute("busId") : Long.valueOf(request.getParameter("busId"));
        BusDaoImpl busDao = (BusDaoImpl) getServletContext().getAttribute("busDao");
        RouteDaoImpl routeDao = (RouteDaoImpl) getServletContext().getAttribute("routeDao");
        DriverDaoImpl driverDao = (DriverDaoImpl) getServletContext().getAttribute("driverDao");
        Bus bus = busDao.findById(busId);
        /*action busAdd doesn't processes*/
        switch (action) {
            case "busSelect" -> {
            }
            case "routeAssign" -> {
                Long routeId = Long.valueOf(request.getParameter("selectedRoute"));
                Route busRoute = routeDao.findById(routeId);
                bus.setRoute(busRoute);
                busDao.update(bus);
            }
            case "driverAssign" -> {
                Long driverId = Long.valueOf(request.getParameter("selectedDriver"));
                Driver driver = driverDao.findById(driverId);
                driver.getBuses().add(bus);
                driverDao.update(driver);
            }
            case "driverRemove" -> {
                Long driverId = Long.valueOf(request.getParameter("selectedDriver"));
                driverDao.removeBusFromDriver(driverId, bus);
            }
        }

        List<Route> otherRoutes = routeDao.findAll();
        Route busRoute = routeDao.findByBus(bus);
        otherRoutes.remove(busRoute);
        List<Driver> otherDrivers = driverDao.findAll();
        List<Driver> busDrivers = driverDao.findByBus(bus);
        otherDrivers.removeAll(busDrivers);

        String nextUrl = "busassignment";
        request.setAttribute("bus", bus);
        request.setAttribute("otherRoutes", otherRoutes);
        request.setAttribute("busDrivers", bus.getDrivers());
        request.setAttribute("otherDrivers", otherDrivers);
        request.setAttribute("nextUrl", nextUrl);

        response.setContentType("text/html;charset=UTF-8");
    }
}
