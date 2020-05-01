package pl.ug.citycourier.internal.algorithm.dto;

public class CourierToDeliveryStartPath implements Comparable<CourierToDeliveryStartPath> {
    Path path;
    DeliveryInAlgorithm deliveryInAlgorithm;

    public CourierToDeliveryStartPath(Path path, DeliveryInAlgorithm deliveryInAlgorithm) {
        this.path = path;
        this.deliveryInAlgorithm = deliveryInAlgorithm;
    }

    public boolean isDeliveryAssigned() {
        return deliveryInAlgorithm.isAssigned();
    }

    public Path getPath() {
        return path;
    }

    public DeliveryInAlgorithm getDeliveryInAlgorithm() {
        return deliveryInAlgorithm;
    }

    @Override
    public int compareTo(CourierToDeliveryStartPath o) {
        return path.compareTo(o.path);
    }
}
