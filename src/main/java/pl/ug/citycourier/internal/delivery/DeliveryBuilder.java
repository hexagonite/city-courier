package pl.ug.citycourier.internal.delivery;

import pl.ug.citycourier.internal.location.Location;
import pl.ug.citycourier.internal.pack.Pack;
import pl.ug.citycourier.internal.user.User;

public final class DeliveryBuilder {
    private long id;
    private Pack pack;
    private User client;
    private User courier;
    private Location start;
    private Location destination;

    private DeliveryBuilder() {
    }

    public static DeliveryBuilder aDelivery() {
        return new DeliveryBuilder();
    }

    public DeliveryBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public DeliveryBuilder withPack(Pack pack) {
        this.pack = pack;
        return this;
    }

    public DeliveryBuilder withClient(User client) {
        this.client = client;
        return this;
    }

    public DeliveryBuilder withCourier(User courier) {
        this.courier = courier;
        return this;
    }

    public DeliveryBuilder withStart(Location start) {
        this.start = start;
        return this;
    }

    public DeliveryBuilder withDestination(Location destination) {
        this.destination = destination;
        return this;
    }

    public Delivery build() {
        Delivery delivery = new Delivery();
        delivery.setId(id);
        delivery.setPack(pack);
        delivery.setClient(client);
        delivery.setCourier(courier);
        delivery.setStart(start);
        delivery.setDestination(destination);
        return delivery;
    }
}
