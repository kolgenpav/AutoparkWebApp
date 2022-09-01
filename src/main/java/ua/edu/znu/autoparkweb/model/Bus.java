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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "buses")
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "number", nullable = false, unique = true, length = 10)
    private String number;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    //TODO CascadeType.REMOVE deletes driver while bus remove!!!
    @ManyToMany(mappedBy = "buses", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Driver> drivers = new LinkedHashSet<>();
}
