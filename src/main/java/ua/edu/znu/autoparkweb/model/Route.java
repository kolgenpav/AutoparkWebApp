package ua.edu.znu.autoparkweb.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Bus route.
 */
@Data
@Entity
@Table(name = "routes")
public class Route {
    /**
     * Empty route settled to bus while new bus creation to NullPoinerException avoid.
     */
    @Transient
    private static Route emptyRoute;
    public static Route getEmptyRoute(){
        if(emptyRoute == null){
            Route emptyRoute = new Route();
            emptyRoute.setName("EMPTY ROUTE - DON'T REMOVE THIS ROUTE!!!");
            emptyRoute.setNumber(-1);
            return emptyRoute;
        }
        return emptyRoute;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false, unique = true, length = 150)
    private String name;
    @Column(name = "number", nullable = false, unique = true)
    private int number;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "route", cascade = CascadeType.PERSIST)
    private Set<Bus> buses = new LinkedHashSet<>();
}
