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
import ua.edu.znu.autoparkweb.model.dto.BusAssignment;
import ua.edu.znu.autoparkweb.service.BusDaoImpl;
import ua.edu.znu.autoparkweb.service.DriverDaoImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The start point for the authenticated user.
 */
@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {
    private TemplateEngine templateEngine;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.templateEngine = (TemplateEngine) getServletContext()
                .getAttribute(ThymeleafConfiguration.TEMPLATE_ENGINE_ATR);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {
        String message = (String) request.getAttribute("message");
        WebContext context = getWebContext(request, response);
        BusDaoImpl busDao = new BusDaoImpl();
        List<Bus> buses = busDao.findAll();
        List<BusAssignment> busAssignments = new ArrayList<>();
        for (Bus bus : buses) {
            BusAssignment busAssignment = new BusAssignment();
            busAssignment.setBusId(bus.getId());
            busAssignment.setBusNumber(bus.getNumber());
            busAssignment.setRouteNumber(bus.getRoute().getNumber());
            busAssignment.setRouteName(bus.getRoute().getName());
            StringBuilder driversInfo = new StringBuilder();
            DriverDaoImpl driverDao = new DriverDaoImpl();
            List<Driver> busDrivers = driverDao.findByBus(bus);
            for (Driver driver : busDrivers) {
                driversInfo.append(driver.getSurname())
                        .append(" ")
                        .append(driver.getName().charAt(0))
                        .append(". ")
                        .append(driver.getAge())
                        .append(" years\n");
            }
            busAssignment.setDriversInfo(driversInfo.toString());
            busAssignments.add(busAssignment);
        }

        context.setVariable("busAssignments", busAssignments);
        context.setVariable("message", message);

        templateEngine.process("home", context, response.getWriter());
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
