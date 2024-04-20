package ua.edu.znu.autoparkweb.conroller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.edu.znu.autoparkweb.model.Bus;
import ua.edu.znu.autoparkweb.service.BusDaoImpl;

import java.io.IOException;

/**
 * Edits existing bus.
 */
@WebServlet("/BusEditServlet")
public class BusEditServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {
        BusDaoImpl busDao = (BusDaoImpl) getServletContext().getAttribute("busDao");
        Long busId = Long.valueOf(request.getParameter("busId"));
        Bus bus = busDao.findById(busId);
        String nextUrl = "busedit";
        request.setAttribute("bus", bus);
        request.setAttribute("nextUrl", nextUrl);
        response.setContentType("text/html;charset=UTF-8");
    }
}
