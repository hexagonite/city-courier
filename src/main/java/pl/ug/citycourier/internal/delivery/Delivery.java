package pl.ug.citycourier.internal.delivery;

import pl.ug.citycourier.internal.location.Location;
import pl.ug.citycourier.internal.pack.Pack;
import pl.ug.citycourier.internal.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "packId", referencedColumnName = "id", nullable = false)
    private Pack pack;

    @ManyToOne
    @JoinColumn(name = "clientId", nullable = false)
    private User client;

    @ManyToOne
    @JoinColumn(name = "courierId")
    private User courier;

    @ManyToOne
    @JoinColumn(name = "startLocationId")
    private Location start;

    @ManyToOne
    @JoinColumn(name = "destinationLocationId")
    private Location destination;

    private LocalDateTime getPackDate;
    private LocalDateTime deliverPackDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Pack getPack() {
        return pack;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public User getCourier() {
        return courier;
    }

    public void setCourier(User courier) {
        this.courier = courier;
    }

    public Location getStart() {
        return start;
    }

    public void setStart(Location start) {
        this.start = start;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public LocalDateTime getGetPackDate() {
        return getPackDate;
    }

    public void setGetPackDate(LocalDateTime getPackDate) {
        this.getPackDate = getPackDate;
    }

    public LocalDateTime getDeliverPackDate() {
        return deliverPackDate;
    }

    public void setDeliverPackDate(LocalDateTime deliverPackDate) {
        this.deliverPackDate = deliverPackDate;
    }
}
