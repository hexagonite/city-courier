package pl.ug.citycourier.internal.delivery;

import pl.ug.citycourier.internal.common.EntityNotFoundException;

public class DeliveryNotFoundException extends EntityNotFoundException {
    public DeliveryNotFoundException() {
        super("Not found delivery entity!");
    }
}
