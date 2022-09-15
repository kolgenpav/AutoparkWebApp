package ua.edu.znu.autoparkweb.model.dto;

import lombok.Data;

/**
 * DTO for busassignment.html,
 * used in the {@link ua.edu.znu.autoparkweb.conroller.BusAssignmentServlet}.
 */
@Data
public class BusAssignment {
    private Long busId;
    private String busNumber;
    private int routeNumber;
    private String routeName;
    private String driversInfo;
}
