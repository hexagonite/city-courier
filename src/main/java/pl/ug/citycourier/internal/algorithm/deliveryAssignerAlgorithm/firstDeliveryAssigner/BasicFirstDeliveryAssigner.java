package pl.ug.citycourier.internal.algorithm.deliveryAssignerAlgorithm.firstDeliveryAssigner;

import org.springframework.stereotype.Component;
import pl.ug.citycourier.internal.algorithm.dto.CourierInAlgorithm;
import pl.ug.citycourier.internal.algorithm.dto.DeliveryInAlgorithm;
import pl.ug.citycourier.internal.algorithm.dto.Path;
import pl.ug.citycourier.internal.algorithm.dto.PathToDelivery;
import pl.ug.citycourier.internal.algorithm.exception.InternalAlgorithmException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class BasicFirstDeliveryAssigner implements FirstDeliveryAssigner {

    @Override
    public void assignFirstDeliveries(List<CourierInAlgorithm> couriers) throws InternalAlgorithmException {
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
                    if (path.getPath().equals(minPath)) {
                        minPath = path.getPath();
                        courierFirstDelivery = path.getDeliveryInAlgorithm();
                        assigneeIndex = courierIndex;
                    }
                }
            }
            assertResults(assigneeIndex, courierFirstDelivery);
            couriers.get(assigneeIndex).assignDelivery(new PathToDelivery(minPath, courierFirstDelivery));
            courierFirstDelivery.setAssigned(true);
            couriersLeftIndices.remove(assigneeIndex);
        } while (!couriersLeftIndices.isEmpty());
    }

    private boolean doesCourierHaveEmptyList(CourierInAlgorithm courier) {
        return courier.getPathsFromCourierStartToDeliveryStarts().isEmpty();
    }

    private void assertResults(Integer assigneeIndex,
                               DeliveryInAlgorithm courierFirstDelivery) throws InternalAlgorithmException {
        if (assigneeIndex == null || courierFirstDelivery == null) {
            throw new InternalAlgorithmException("Invalid assignee index");
        }
    }

}
