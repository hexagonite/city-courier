package pl.ug.citycourier.internal.delivery;

import pl.ug.citycourier.internal.common.EntityNotFoundException;

public class DeliveryNotFoundException extends EntityNotFoundException {
    public DeliveryNotFoundException() {
        super("Delivery entity not found!");
    }
}
