package ua.edu.znu.autoparkweb.model.dto;

import lombok.Data;
import ua.edu.znu.autoparkweb.model.Driver;

import java.util.List;

@Data
public class BusAssignment {
    private Long busId;
    private String busNumber;
    private int routeNumber;
    private String routeName;
    private String driversInfo;
}
