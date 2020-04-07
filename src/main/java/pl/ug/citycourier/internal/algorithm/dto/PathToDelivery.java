package pl.ug.citycourier.internal.algorithm.dto;

import pl.ug.citycourier.internal.delivery.Delivery;

public class PathToDelivery {
    private Path path;
    private Delivery delivery;

    public PathToDelivery(Path path, Delivery delivery) {
        this.path = path;
        this.delivery = delivery;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
}
