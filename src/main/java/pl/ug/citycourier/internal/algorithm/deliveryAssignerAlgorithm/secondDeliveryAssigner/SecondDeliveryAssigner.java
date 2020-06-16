package pl.ug.citycourier.internal.algorithm.deliveryAssignerAlgorithm.secondDeliveryAssigner;

import pl.ug.citycourier.internal.algorithm.dto.CourierInAlgorithm;
import pl.ug.citycourier.internal.delivery.DeliveryNotFoundException;

import java.util.List;

public interface SecondDeliveryAssigner {

    void assignSecondDeliveries(List<CourierInAlgorithm> couriers) throws DeliveryNotFoundException;
}
