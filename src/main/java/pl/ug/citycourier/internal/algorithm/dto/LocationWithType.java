package pl.ug.citycourier.internal.algorithm.dto;

import pl.ug.citycourier.internal.courier.CourierTaskType;
import pl.ug.citycourier.internal.location.Location;

public class LocationWithType {
    private Location location;
    private CourierTaskType courierTaskType;

    public LocationWithType(Location location, CourierTaskType courierTaskType) {
        this.location = location;
        this.courierTaskType = courierTaskType;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public CourierTaskType getCourierTaskType() {
        return courierTaskType;
    }

    public void setCourierTaskType(CourierTaskType courierTaskType) {
        this.courierTaskType = courierTaskType;
    }
}
