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
import ua.edu.znu.autoparkweb.service.BusDaoImpl;
import ua.edu.znu.autoparkweb.service.DriverDaoImpl;

import java.io.IOException;
import java.util.List;

/**
 * The start point for the authenticated user.
 */
@WebServlet("/BusesServlet")
public class BusesServlet extends HttpServlet {
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
        WebContext context = getWebContext(request, response);
        BusDaoImpl busDao = new BusDaoImpl();
        List<Bus> buses = busDao.findAll();
//        System.out.println("Buses size is: " + buses.size());

        context.setVariable("buses", buses);
        templateEngine.process("buses", context, response.getWriter());
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {
        WebContext context = getWebContext(request, response);
        String action = request.getParameter("action");
        Bus bus;
        List<Bus> buses;
        BusDaoImpl busDao = new BusDaoImpl();
        DriverDaoImpl driverDao = new DriverDaoImpl();
        switch (action) {
            //TODO not worked properly
            case "busEdit" -> {
                long busId = Long.parseLong(request.getParameter("busId"));
                bus = busDao.findById(busId);
                String busNumber = request.getParameter("editBusNumber");
                bus.setNumber(busNumber);
                busDao.update(bus);
                context.setVariable("bus", bus);
                templateEngine.process("busedit", context, response.getWriter());
            }
            case "busRemove" -> {
                long busId = Long.parseLong(request.getParameter("busId"));
                bus = busDao.findById(busId);
                /*You need delete bus's drivers first due to bidirectional many-to-many association */
                for (Driver driver : bus.getDrivers()) {
                    driver.getBuses().remove(bus);
                    driverDao.update(driver);
                }
                busDao.delete(bus);
                buses = busDao.findAll();
                context.setVariable("buses", buses);
                templateEngine.process("buses", context, response.getWriter());
            }
            default -> {
                buses = busDao.findAll();
                context.setVariable("buses", buses);

                templateEngine.process("buses", context, response.getWriter());
            }
        }

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
