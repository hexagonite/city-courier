package pl.ug.citycourier.internal.location;

import pl.ug.citycourier.internal.coordinate.CoordinatePair;
import pl.ug.citycourier.internal.delivery.Delivery;

import java.util.Set;

public final class LocationBuilder {
    private long id;
    private CoordinatePair coordinatePair;
    private String city;
    private String street;
    private String houseNumber;
    private String zipCode;
    private Set<Delivery> startDeliveries;
    private Set<Delivery> destDeliveries;

    private LocationBuilder() {
    }

    public static LocationBuilder aLocation() {
        return new LocationBuilder();
    }

    public LocationBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public LocationBuilder withCoordinatePair(CoordinatePair coordinatePair) {
        this.coordinatePair = coordinatePair;
        return this;
    }

    public LocationBuilder withCity(String city) {
        this.city = city;
        return this;
    }

    public LocationBuilder withStreet(String street) {
        this.street = street;
        return this;
    }

    public LocationBuilder withHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    public LocationBuilder withZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public LocationBuilder withStartDeliveries(Set<Delivery> startDeliveries) {
        this.startDeliveries = startDeliveries;
        return this;
    }

    public LocationBuilder withDestDeliveries(Set<Delivery> destDeliveries) {
        this.destDeliveries = destDeliveries;
        return this;
    }

    public Location build() {
        Location location = new Location();
        location.setId(id);
        location.setCoordinatePair(coordinatePair);
        location.setCity(city);
        location.setStreet(street);
        location.setHouseNumber(houseNumber);
        location.setZipCode(zipCode);
        location.setStartDeliveries(startDeliveries);
        location.setDestDeliveries(destDeliveries);
        return location;
    }
}
