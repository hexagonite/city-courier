package pl.ug.citycourier.internal.algorithm.deliveryAssignerAlgorithm;

import pl.ug.citycourier.internal.algorithm.dto.CourierInAlgorithm;
import pl.ug.citycourier.internal.algorithm.dto.DeliveryInAlgorithm;
import pl.ug.citycourier.internal.algorithm.exception.InternalAlgorithmException;
import pl.ug.citycourier.internal.courier.CourierJob;
import pl.ug.citycourier.internal.user.UserNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Queue;

public interface DeliveryAssigner {

    Collection<CourierJob> run(List<DeliveryInAlgorithm> deliveries, List<CourierInAlgorithm> couriers) throws InternalAlgorithmException, UserNotFoundException;
}
