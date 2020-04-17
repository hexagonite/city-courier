package pl.ug.citycourier.internal.algorithm.deliveryAssignerAlgorithm;

import pl.ug.citycourier.internal.algorithm.dto.CourierInAlgorithm;
import pl.ug.citycourier.internal.algorithm.dto.DeliveryInAlgorithm;
import pl.ug.citycourier.internal.algorithm.exception.InternalAlgorithmException;
import pl.ug.citycourier.internal.courier.CourierJob;

import java.util.List;
import java.util.Queue;

public interface DeliveryAssigner {

    Queue<CourierJob> run(List<DeliveryInAlgorithm> deliveries, List<CourierInAlgorithm> couriers) throws InternalAlgorithmException;
}
