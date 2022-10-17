package ua.edu.znu.autoparkweb.conroller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.edu.znu.autoparkweb.model.Bus;
import ua.edu.znu.autoparkweb.model.Driver;
import ua.edu.znu.autoparkweb.service.BusDaoImpl;
import ua.edu.znu.autoparkweb.service.DriverDaoImpl;

import java.io.IOException;
import java.util.List;

/**
 * Provides operations with Driver directory.
 */
@WebServlet("/DriversServlet")
public class DriversServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) {
        DriverDaoImpl driverDao = (DriverDaoImpl) getServletContext().getAttribute("driverDao");
        List<Driver> drivers = driverDao.findAll();
        String nextUrl = "drivers";
        request.setAttribute("drivers", drivers);
        request.setAttribute("nextUrl", nextUrl);
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {
        String action = request.getParameter("action");
        DriverDaoImpl driverDao = (DriverDaoImpl) getServletContext().getAttribute("driverDao");
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
                BusDaoImpl busDao = (BusDaoImpl) getServletContext().getAttribute("busDao");
                /*You need to remove driver's buses first*/
                for (Bus bus : driver.getBuses()) {
                    bus.getDrivers().remove(driver);
                    busDao.update(bus);
                }
                driverDao.delete(driver);
            }
        }
        String nextUrl = "drivers";
        request.setAttribute("nextUrl", nextUrl);
        /*Make GET request to this servlet*/
        response.sendRedirect(request.getContextPath() + "/DriversServlet");
    }
}
