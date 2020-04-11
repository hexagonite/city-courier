package pl.ug.citycourier.internal.algorithm.dto;

public class PathToDelivery {
    private Path path;
    private DeliveryInAlgorithm delivery;

    public PathToDelivery(Path path, DeliveryInAlgorithm delivery) {
        this.path = path;
        this.delivery = delivery;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public DeliveryInAlgorithm getDelivery() {
        return delivery;
    }

    public void setDelivery(DeliveryInAlgorithm delivery) {
        this.delivery = delivery;
    }
}
