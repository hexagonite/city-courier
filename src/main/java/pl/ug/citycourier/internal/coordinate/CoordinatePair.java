package pl.ug.citycourier.internal.coordinate;

import pl.ug.citycourier.internal.location.Location;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class CoordinatePair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private double latitude;
    @NotNull
    private double longitude;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locationId")
    private Location location;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
