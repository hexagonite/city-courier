package pl.ug.citycourier.internal.algorithm.deliveryAssignerAlgorithm.firstDeliveryAssigner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.ug.citycourier.internal.algorithm.dto.CourierInAlgorithm;
import pl.ug.citycourier.internal.algorithm.dto.DeliveryInAlgorithm;
import pl.ug.citycourier.internal.algorithm.dto.Path;
import pl.ug.citycourier.internal.algorithm.dto.PathToDelivery;
import pl.ug.citycourier.internal.algorithm.exception.InternalAlgorithmException;
import pl.ug.citycourier.internal.delivery.DeliveryNotFoundException;
import pl.ug.citycourier.internal.delivery.DeliveryService;
import pl.ug.citycourier.internal.user.Status;
import pl.ug.citycourier.internal.user.UserNotFoundException;
import pl.ug.citycourier.internal.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class BasicFirstDeliveryAssigner implements FirstDeliveryAssigner {

    private DeliveryService deliveryService;
    private UserService userService;

    @Autowired
    public BasicFirstDeliveryAssigner(DeliveryService deliveryService, UserService userService) {
        this.deliveryService = deliveryService;
        this.userService = userService;
    }

    @Override
    public void assignFirstDeliveries(List<CourierInAlgorithm> couriers) throws InternalAlgorithmException, UserNotFoundException, DeliveryNotFoundException {
        List<Integer> couriersLeftIndices =
                IntStream.range(0, couriers.size()).boxed().collect(Collectors.toCollection(ArrayList::new));
        do {
            Path minPath = new Path(Double.MAX_VALUE);
            DeliveryInAlgorithm courierFirstDelivery = null;
            Integer assigneeIndex = null;
            for (int courierIndex : couriersLeftIndices) {
                if (doesCourierHaveEmptyList(couriers.get(courierIndex))) {
                    couriersLeftIndices.remove(assigneeIndex);
                } else {
                    var path = couriers.get(courierIndex).getNearestPathToDeliveryWithRemoval();
                    if (path.getPath().compareTo(minPath) < 0) {
                        minPath = path.getPath();
                        courierFirstDelivery = path.getDeliveryInAlgorithm();
                        assigneeIndex = courierIndex;
                    }
                }
            }
            assertResults(assigneeIndex, courierFirstDelivery);
            couriers.get(assigneeIndex).assignDelivery(new PathToDelivery(minPath, courierFirstDelivery));
            deliveryService.assignDeliveryToCourier(courierFirstDelivery, couriers.get(assigneeIndex).getCourier());
            String courierUsername = couriers.get(assigneeIndex).getCourier().getUsername();
            userService.updateStatus(courierUsername, Status.NOT_AVAILABLE);
            couriersLeftIndices.remove(assigneeIndex);
        } while (!couriersLeftIndices.isEmpty());
    }

    private boolean doesCourierHaveEmptyList(CourierInAlgorithm courier) {
        return courier.getCourierToDeliveryStartPaths().isEmpty();
    }

    private void assertResults(Integer assigneeIndex,
                               DeliveryInAlgorithm courierFirstDelivery) throws InternalAlgorithmException {
        if (assigneeIndex == null || courierFirstDelivery == null) {
            throw new InternalAlgorithmException("Invalid assignee index");
        }
    }

}
