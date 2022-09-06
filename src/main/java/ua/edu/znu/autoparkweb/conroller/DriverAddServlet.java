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
import ua.edu.znu.autoparkweb.model.Driver;
import ua.edu.znu.autoparkweb.service.DriverDaoImpl;

import java.io.IOException;

/**
 * Adds a new driver.
 */
@WebServlet("/DriverAddServlet")
public class DriverAddServlet extends HttpServlet {
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
        templateEngine.process("driveradd", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {
        DriverDaoImpl driverDao = new DriverDaoImpl();
        Driver driver = new Driver();
        String driverName = request.getParameter("driverName");
        driver.setName(driverName);
        String driverSurname = request.getParameter("driverSurname");
        driver.setSurname(driverSurname);
        int driverAge = Integer.parseInt(request.getParameter("driverAge"));
        driver.setAge(driverAge);
        driverDao.create(driver);
        /*Make GET request*/
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
