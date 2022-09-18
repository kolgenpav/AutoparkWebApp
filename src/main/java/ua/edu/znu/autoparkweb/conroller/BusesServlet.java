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
 * Provides operations with Bus directory.
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

        context.setVariable("buses", buses);
        templateEngine.process("buses", context, response.getWriter());
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {
        String action = request.getParameter("action");
        BusDaoImpl busDao = new BusDaoImpl();
        long busId = Long.parseLong(request.getParameter("busId"));
        Bus bus = busDao.findById(busId);
        switch (action) {
            case "busEdit" -> {
                String busNumber = request.getParameter("busNumber");
                bus.setNumber(busNumber);
                busDao.update(bus);
            }
            case "busRemove" -> {
                DriverDaoImpl driverDao = new DriverDaoImpl();
                /*You need delete the bus's drivers first due to bidirectional many-to-many association */
                for (Driver driver : bus.getDrivers()) {
                    driver.getBuses().remove(bus);
                    driverDao.update(driver);
                }
                busDao.delete(bus);
            }
        }
        /*Make GET request to this servlet*/
        response.sendRedirect(request.getContextPath() + "/BusesServlet");
}

    private WebContext getWebContext(HttpServletRequest request, HttpServletResponse response) {
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        return new WebContext(webExchange);
    }
}
