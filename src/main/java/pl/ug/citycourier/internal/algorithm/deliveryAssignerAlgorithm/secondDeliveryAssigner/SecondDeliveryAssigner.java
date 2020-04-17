package pl.ug.citycourier.internal.algorithm.deliveryAssignerAlgorithm.secondDeliveryAssigner;

import pl.ug.citycourier.internal.algorithm.dto.CourierInAlgorithm;

import java.util.List;

public interface SecondDeliveryAssigner {

    void assignSecondDeliveries(List<CourierInAlgorithm> couriers);
}
