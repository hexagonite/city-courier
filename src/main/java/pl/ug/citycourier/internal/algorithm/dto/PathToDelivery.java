package pl.ug.citycourier.internal.algorithm.dto;

public class PathToDelivery implements Comparable<PathToDelivery> {
    private Path path;
    private DeliveryInAlgorithm deliveryInAlgorithm;

    public PathToDelivery(Path path, DeliveryInAlgorithm deliveryInAlgorithm) {
        this.path = path;
        this.deliveryInAlgorithm = deliveryInAlgorithm;
    }

    public PathToDelivery(CourierToDeliveryStartPath courierToDeliveryStartPath) {
        this.path = courierToDeliveryStartPath.getPath();
        this.deliveryInAlgorithm = courierToDeliveryStartPath.getDeliveryInAlgorithm();
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public DeliveryInAlgorithm getDeliveryInAlgorithm() {
        return deliveryInAlgorithm;
    }

    public void setDeliveryInAlgorithm(DeliveryInAlgorithm deliveryInAlgorithm) {
        this.deliveryInAlgorithm = deliveryInAlgorithm;
    }

    @Override
    public int compareTo(PathToDelivery pathToDelivery) {
        return this.path.compareTo(pathToDelivery.path);
    }
}
