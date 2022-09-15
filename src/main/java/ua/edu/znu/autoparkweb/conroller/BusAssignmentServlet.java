package ua.edu.znu.autoparkweb.conroller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
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
    private TemplateEngine templateEngine;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.templateEngine = (TemplateEngine) getServletContext()
                .getAttribute(ThymeleafConfiguration.TEMPLATE_ENGINE_ATR);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {
        WebContext context = getWebContext(request, response);
        /*busId passed as parameter from home.html and forwarded as request attribute
         * from BusAddServlet*/
        String action = request.getParameter("action") == null
                ? (String) request.getAttribute("action") : request.getParameter("action");
        long busId = request.getParameter("busId") == null
                ? (long) request.getAttribute("busId") : Long.parseLong(request.getParameter("busId"));
        BusDaoImpl busDao = new BusDaoImpl();
        RouteDaoImpl routeDao = new RouteDaoImpl();
        DriverDaoImpl driverDao = new DriverDaoImpl();
        Bus bus = busDao.findById(busId);
        switch (action) {
            case "busSelect" -> {
            }
            case "routeAssign" -> {
                long routeId = Long.parseLong(request.getParameter("selectedRoute"));
                Route busRoute = routeDao.findById(routeId);
                bus.setRoute(busRoute);
                busDao.update(bus);
            }
            case "driverAssign" -> {
                long driverId = Long.parseLong(request.getParameter("selectedDriver"));
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

        context.setVariable("bus", bus);
        context.setVariable("otherRoutes", otherRoutes);
        context.setVariable("busDrivers", bus.getDrivers());
        context.setVariable("otherDrivers", otherDrivers);
        templateEngine.process("busassignment", context, response.getWriter());
        response.setContentType("text/html;charset=UTF-8");
    }

    private WebContext getWebContext(HttpServletRequest request, HttpServletResponse response) {
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        return new WebContext(webExchange);
    }

    @Override
    public String getServletInfo() {
        return "The start point for the authenticated user.";
    }
}
