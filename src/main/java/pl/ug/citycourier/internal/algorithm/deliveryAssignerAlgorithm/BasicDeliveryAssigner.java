package pl.ug.citycourier.internal.algorithm.deliveryAssignerAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.ug.citycourier.internal.algorithm.courierJobCreator.CourierJobCreator;
import pl.ug.citycourier.internal.algorithm.deliveryAssignerAlgorithm.firstDeliveryAssigner.FirstDeliveryAssigner;
import pl.ug.citycourier.internal.algorithm.deliveryAssignerAlgorithm.secondDeliveryAssigner.SecondDeliveryAssigner;
import pl.ug.citycourier.internal.algorithm.dto.CourierInAlgorithm;
import pl.ug.citycourier.internal.algorithm.dto.DeliveryInAlgorithm;
import pl.ug.citycourier.internal.algorithm.dto.Path;
import pl.ug.citycourier.internal.algorithm.exception.InternalAlgorithmException;
import pl.ug.citycourier.internal.algorithm.pathfinder.Pathfinder;
import pl.ug.citycourier.internal.courier.CourierJob;
import pl.ug.citycourier.internal.location.Location;

import java.util.List;
import java.util.Queue;

/**
 * Assign a maximum of 2 deliveries each courier. Couriers are first assigned first delivery,
 * then second, if applicable. First delivery is assigned based on the shortest path from his current position.
 * Second delivery is assigned based on CHOICES_FOR_SECOND_DELIVERY_AMOUNT other closest delivery starts
 * and one of those deliveries is assigned by calculating every possible path length for each delivery and choosing
 * the minimum of minimums.
 */
@Component
public class BasicDeliveryAssigner implements DeliveryAssigner {

    private Pathfinder pathfinder;
    private FirstDeliveryAssigner firstDeliveryAssigner;
    private SecondDeliveryAssigner secondDeliveryAssigner;
    private CourierJobCreator courierJobCreator;

    @Autowired
    public BasicDeliveryAssigner(@Qualifier("LinearPathFinder") Pathfinder linearPathfinder,
                                 FirstDeliveryAssigner firstDeliveryAssigner,
                                 SecondDeliveryAssigner secondDeliveryAssigner,
                                 CourierJobCreator courierJobCreator) {
        this.pathfinder = linearPathfinder;
        this.firstDeliveryAssigner = firstDeliveryAssigner;
        this.secondDeliveryAssigner = secondDeliveryAssigner;
        this.courierJobCreator = courierJobCreator;
    }

    @Override
    public Queue<CourierJob> run(List<DeliveryInAlgorithm> deliveries, List<CourierInAlgorithm> couriers)
            throws InternalAlgorithmException {
        findDistancesBetweenAllCouriersAndAllDeliveries(deliveries, couriers);
        firstDeliveryAssigner.assignFirstDeliveries(couriers);
        secondDeliveryAssigner.assignSecondDeliveries(couriers);
        return courierJobCreator.createCourierJobs(couriers);
    }

    private void findDistancesBetweenAllCouriersAndAllDeliveries(List<DeliveryInAlgorithm> deliveries,
                                                                 List<CourierInAlgorithm> couriers) {
        for (CourierInAlgorithm courier : couriers) {
            for (DeliveryInAlgorithm delivery : deliveries) {
                Location courierStart = courier.getLocation();
                Location deliveryStart = delivery.getStart();
                Path path = pathfinder.findShortestPath(courierStart, deliveryStart);
                courier.addPath(path, delivery);
            }
        }
    }

}
