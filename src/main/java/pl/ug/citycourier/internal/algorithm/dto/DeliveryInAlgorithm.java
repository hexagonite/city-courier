package pl.ug.citycourier.internal.algorithm.dto;

import pl.ug.citycourier.internal.delivery.Delivery;

public class DeliveryInAlgorithm extends Delivery {
    private boolean isAssigned = false;

    public DeliveryInAlgorithm(Delivery delivery) {
        this.setId(delivery.getId());
        this.setPack(delivery.getPack());
        this.setClient(delivery.getClient());
        this.setCourier(delivery.getCourier());
        this.setStart(delivery.getStart());
        this.setDestination(delivery.getDestination());
    }

    public boolean isAssigned() {
        return isAssigned;
    }

    public void setAssigned(boolean assigned) {
        isAssigned = assigned;
    }
}
