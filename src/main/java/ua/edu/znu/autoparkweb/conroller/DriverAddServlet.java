package ua.edu.znu.autoparkweb.conroller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.edu.znu.autoparkweb.model.Driver;
import ua.edu.znu.autoparkweb.service.DriverDaoImpl;

import java.io.IOException;

/**
 * Adds a new driver.
 */
@WebServlet("/DriverAddServlet")
public class DriverAddServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) {
        String nextUrl = "driveradd";
        request.setAttribute("nextUrl", nextUrl);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {
        DriverDaoImpl driverDao = (DriverDaoImpl) getServletContext().getAttribute("driverDao");
        Driver driver = new Driver();
        String driverName = request.getParameter("driverName");
        driver.setName(driverName);
        String driverSurname = request.getParameter("driverSurname");
        driver.setSurname(driverSurname);
        int driverAge = Integer.parseInt(request.getParameter("driverAge"));
        driver.setAge(driverAge);
        driverDao.create(driver);
        String nextUrl = "drivers";
        request.setAttribute("nextUrl", nextUrl);
        /*Make GET request*/
        response.sendRedirect(request.getContextPath() + "/DriversServlet");
    }
}
