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
 * Edits existing driver.
 */
@WebServlet("/DriverEditServlet")
public class DriverEditServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {
        DriverDaoImpl driverDao = (DriverDaoImpl) getServletContext().getAttribute("driverDao");
        Long driverId = Long.valueOf(request.getParameter("driverId"));
        Driver driver = driverDao.findById(driverId);
        String nextUrl = "driveredit";
        request.setAttribute("driver", driver);
        request.setAttribute("nextUrl", nextUrl);
        response.setContentType("text/html;charset=UTF-8");
    }
}
