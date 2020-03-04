package pl.ug.citycourier.internal.delivery;

import pl.ug.citycourier.internal.pack.Pack;
import pl.ug.citycourier.internal.user.User;

import javax.persistence.*;

@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "packId", referencedColumnName = "id", nullable = false)
    private Pack pack;

    @ManyToOne
    @JoinColumn(name = "clientId", nullable = false)
    private User client;

    @ManyToOne
    @JoinColumn(name = "courierId")
    private User courier;

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
}
