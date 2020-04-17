package pl.ug.citycourier.internal.algorithm.courierJobCreator;

import pl.ug.citycourier.internal.algorithm.dto.CourierInAlgorithm;
import pl.ug.citycourier.internal.algorithm.dto.LocationWithType;
import pl.ug.citycourier.internal.courier.CourierJob;
import pl.ug.citycourier.internal.courier.CourierTask;
import pl.ug.citycourier.internal.delivery.Delivery;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BasicCourierJobCreator implements CourierJobCreator {

    @Override
    public Queue<CourierJob> createCourierJobs(List<CourierInAlgorithm> couriers) {
        return couriers.stream()
                .map(courier -> new CourierJob(createCourierTasks(courier), courier.getCourier()))
                .collect(Collectors.toCollection(PriorityQueue::new));
    }

    private Queue<CourierTask> createCourierTasks(CourierInAlgorithm courier) {
        var shortestCourierPath = courier.getShortestCourierPath();
        var AS = shortestCourierPath.getLocations().get(0);
        var AF = shortestCourierPath.getLocations().get(1);
        var BS = shortestCourierPath.getLocations().get(2);
        var BF = shortestCourierPath.getLocations().get(3);
        var delivery = (Delivery) shortestCourierPath.getEntirePath().getDeliveryInAlgorithm();
        switch (shortestCourierPath.getIndex()) {
            case 0:
                return createCourierTasksHelper(delivery, AS, AF, BS, BF);
            case 1:
                return createCourierTasksHelper(delivery, AS, BS, BF, AF);
            case 2:
                return createCourierTasksHelper(delivery, AS, BS, AF, BF);
            case 3:
                return createCourierTasksHelper(delivery, BS, BF, AS, AF);
            case 4:
                return createCourierTasksHelper(delivery, BS, AS, AF, BF);
            case 5:
                return createCourierTasksHelper(delivery, BS, AS, BF, AF);
        }
         return new PriorityQueue<>();
    }

    private Queue<CourierTask> createCourierTasksHelper(Delivery delivery, LocationWithType... locationsWithType) {
        return Stream.of(locationsWithType)
                .map(locationWithType -> new CourierTask(locationWithType, delivery))
                .collect(Collectors.toCollection(PriorityQueue::new));
    }


//    Queue<Location> locations = new PriorityQueue<>();
//    Queue<CourierTaskType> courierTaskTypes = new PriorityQueue<>();
}
