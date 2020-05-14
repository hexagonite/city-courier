package pl.ug.citycourier.internal.algorithm.deliveryAssignerAlgorithm.firstDeliveryAssigner;

import pl.ug.citycourier.internal.algorithm.dto.CourierInAlgorithm;
import pl.ug.citycourier.internal.algorithm.exception.InternalAlgorithmException;
import pl.ug.citycourier.internal.user.UserNotFoundException;

import java.util.List;

public interface FirstDeliveryAssigner {

    void assignFirstDeliveries(List<CourierInAlgorithm> couriers) throws InternalAlgorithmException, UserNotFoundException;

}
