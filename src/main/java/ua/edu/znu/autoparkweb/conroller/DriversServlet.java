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
 * Populates drivers.html with current list of drivers in the doGet.
 * Performs driver edit and driver remove in the doPost.
 */
@WebServlet("/DriversServlet")
public class DriversServlet extends HttpServlet {
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
        DriverDaoImpl driverDao = new DriverDaoImpl();
        List<Driver> drivers = driverDao.findAll();

        context.setVariable("drivers", drivers);
        templateEngine.process("drivers", context, response.getWriter());
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {
        String action = request.getParameter("action");
        DriverDaoImpl driverDao = new DriverDaoImpl();
        long driverId = Long.parseLong(request.getParameter("driverId"));
        Driver driver = driverDao.findById(driverId);
        switch (action) {
            case "driverEdit" -> {
                String driverName = request.getParameter("driverName");
                driver.setName(driverName);
                String driverSurname = request.getParameter("driverSurname");
                driver.setSurname(driverSurname);
                int driverAge = Integer.parseInt(request.getParameter("driverAge"));
                driver.setAge(driverAge);
                driverDao.update(driver);
            }
            case "driverRemove" -> {
                BusDaoImpl busDao = new BusDaoImpl();
                /*You need to remove driver's buses first*/
                for (Bus bus : driver.getBuses()) {
                    bus.getDrivers().remove(driver);
                    busDao.update(bus);
                }
                driverDao.delete(driver);
            }
        }
        /*Make GET request to this servlet*/
        response.sendRedirect(request.getContextPath() + "/DriversServlet");
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
