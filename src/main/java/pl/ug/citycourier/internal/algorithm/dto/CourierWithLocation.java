package pl.ug.citycourier.internal.algorithm.dto;

import pl.ug.citycourier.internal.location.Location;
import pl.ug.citycourier.internal.user.User;

public class CourierWithLocation {
    private User courier;
    private Location location;

    public CourierWithLocation(User courier, Location location) {
        this.courier = courier;
        this.location = location;
    }

    public User getCourier() {
        return courier;
    }

    public void setCourier(User courier) {
        this.courier = courier;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
