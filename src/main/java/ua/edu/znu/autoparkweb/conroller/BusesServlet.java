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
 * Provides operations with Bus directory.
 */
@WebServlet("/BusesServlet")
public class BusesServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) {
        BusDaoImpl busDao = (BusDaoImpl) getServletContext().getAttribute("busDao");
        List<Bus> buses = busDao.findAll();
        String nextUrl = "buses";
        request.setAttribute("buses", buses);
        request.setAttribute("nextUrl", nextUrl);
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {
        String action = request.getParameter("action");
        BusDaoImpl busDao = (BusDaoImpl) getServletContext().getAttribute("busDao");
        Long busId = Long.valueOf(request.getParameter("busId"));
        Bus bus = busDao.findById(busId);
        switch (action) {
            case "busEdit" -> {
                String busNumber = request.getParameter("busNumber");
                bus.setNumber(busNumber);
                busDao.update(bus);
            }
            case "busRemove" -> {
                DriverDaoImpl driverDao = (DriverDaoImpl) getServletContext().getAttribute("driverDao");
                /*You need delete the bus's drivers first due to bidirectional many-to-many association */
                for (Driver driver : bus.getDrivers()) {
                    driver.getBuses().remove(bus);
                    driverDao.update(driver);
                }
                busDao.delete(bus);
            }
        }
        String nextUrl = "buses";
        request.setAttribute("nextUrl", nextUrl);
        /*Make GET request to this servlet*/
        response.sendRedirect(request.getContextPath() + "/BusesServlet");
    }
}
