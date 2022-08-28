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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The start point for the authenticated user.
 */
@WebServlet("/BusServlet")
public class BusServlet extends HttpServlet {
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
        String action = request.getParameter("action");
        Long busId = Long.valueOf(request.getParameter("busId"));
        BusDaoImpl busDao = new BusDaoImpl();
        Bus bus = busDao.findById(busId);
        DriverDaoImpl driverDao = new DriverDaoImpl();
        switch(action) {
            case "routeEdit" -> {
                Long routeId = Long.valueOf(request.getParameter("selectedRoute"));
                RouteDaoImpl routeDao = new RouteDaoImpl();
                Route route = routeDao.findById(routeId);
                bus.setRoute(route);
                busDao.update(bus);
            }
            case "driverAdd" -> {
                Long driverId = Long.valueOf(request.getParameter("selectedDriver"));
                driverDao.addBusToDriver(driverId,bus);
            }
            case "driverRemove" -> {
                Long driverId = Long.valueOf(request.getParameter("selectedDriver"));
                driverDao.removeBusFromDriver(driverId,bus);
            }
        }

        List<Driver> otherDrivers = driverDao.findAll();
        List<Driver> busDrivers = driverDao.findByBus(bus);
        otherDrivers.removeIf(d -> busDrivers.contains(d));

        context.setVariable("bus", bus);
        context.setVariable("busDrivers", busDrivers);
        context.setVariable("otherDrivers", otherDrivers);

        templateEngine.process("buses", context, response.getWriter());
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
