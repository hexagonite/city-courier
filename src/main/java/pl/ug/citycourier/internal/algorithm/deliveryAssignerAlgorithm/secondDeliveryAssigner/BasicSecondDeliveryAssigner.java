package pl.ug.citycourier.internal.algorithm.deliveryAssignerAlgorithm.secondDeliveryAssigner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.ug.citycourier.internal.algorithm.dto.CourierInAlgorithm;
import pl.ug.citycourier.internal.algorithm.dto.PathToDelivery;
import pl.ug.citycourier.internal.algorithm.possiblePathsFinder.PossiblePathsFinder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BasicSecondDeliveryAssigner implements SecondDeliveryAssigner {

    private final static int CHOICES_FOR_SECOND_DELIVERY_AMOUNT = 3;

    private PossiblePathsFinder possiblePathsFinder;

    @Autowired
    public BasicSecondDeliveryAssigner(PossiblePathsFinder possiblePathsFinder) {
        this.possiblePathsFinder = possiblePathsFinder;
    }

    @Override
    public void assignSecondDeliveries(List<CourierInAlgorithm> couriers) {
        for (var courier : couriers) {
            List<PathToDelivery> potentialDeliveries =
                    courier.getNearestPathsToDeliveriesWithRemoval(CHOICES_FOR_SECOND_DELIVERY_AMOUNT);
            if (!potentialDeliveries.isEmpty()) {
                var shortestPotentialPaths = potentialDeliveries.stream()
                        .map(potentialDelivery ->
                                possiblePathsFinder.chooseShortestPossiblePath(courier, potentialDelivery))
                        .collect(Collectors.toList());
                var shortestCourierPath = Collections.min(shortestPotentialPaths);
                courier.assignDelivery(shortestCourierPath.getEntirePath());
                courier.setShortestCourierPath(shortestCourierPath);
            }
        }
    }

}
