package pl.ug.citycourier.internal.location;

import pl.ug.citycourier.internal.coordinate.CoordinatePair;
import pl.ug.citycourier.internal.delivery.Delivery;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.MERGE, optional = false)
    private CoordinatePair coordinatePair;

    private String city;
    private String street;
    private String houseNumber;
    private String zipCode;

    @OneToMany(mappedBy = "start")
    private Set<Delivery> startDeliveries;

    @OneToMany(mappedBy = "destination")
    private Set<Delivery> destDeliveries;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CoordinatePair getCoordinatePair() {
        return coordinatePair;
    }

    public void setCoordinatePair(CoordinatePair coordinatePair) {
        this.coordinatePair = coordinatePair;
    }

    public Set<Delivery> getStartDeliveries() {
        return startDeliveries;
    }

    public void setStartDeliveries(Set<Delivery> startDeliveries) {
        this.startDeliveries = startDeliveries;
    }

    public Set<Delivery> getDestDeliveries() {
        return destDeliveries;
    }

    public void setDestDeliveries(Set<Delivery> destDeliveries) {
        this.destDeliveries = destDeliveries;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
