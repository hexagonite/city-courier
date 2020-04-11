package pl.ug.citycourier.internal.algorithm.dto;

import pl.ug.citycourier.internal.delivery.Delivery;
import pl.ug.citycourier.internal.location.Location;
import pl.ug.citycourier.internal.user.User;

import java.util.*;
import java.util.stream.Collectors;

public class DeliveryAssignerCourier {
    private CourierWithLocation courierWithLocation;
    private Queue<PathToDelivery> assignedDeliveries = new PriorityQueue<>();
    private NavigableMap<Path, List<Delivery>> pathsFromCourierStartToDeliveryStarts = new TreeMap<>();

    public DeliveryAssignerCourier(CourierWithLocation courierWithLocation) {
        this.courierWithLocation = courierWithLocation;
    }

    public void assignDelivery(PathToDelivery pathToDelivery) {
        assignedDeliveries.add(pathToDelivery);
    }

    public void addPath(Path path, Delivery delivery) {
        pathsFromCourierStartToDeliveryStarts.computeIfAbsent(path, k -> new LinkedList<>());
        pathsFromCourierStartToDeliveryStarts.get(path).add(delivery);
    }

    public PathToDelivery getNearestDelivery() {
        var entry = pathsFromCourierStartToDeliveryStarts.firstEntry();
        return new PathToDelivery(entry.getKey(), entry.getValue().get(0));
    }

    public void removeDelivery(Delivery deliveryToBeRemoved) {
        for (Map.Entry<Path, List<Delivery>> entry : pathsFromCourierStartToDeliveryStarts.entrySet()) {
            var entryDeliveries = entry.getValue();
            boolean removed;
            if (entryDeliveries.size() == 1) {
                removed = pathsFromCourierStartToDeliveryStarts.remove(entry.getKey(), entryDeliveries);
            } else {
                removed = entryDeliveries.remove(deliveryToBeRemoved);
            }
            if (removed) {
                break;
            }
        }
    }

    public User getCourier() {
        return courierWithLocation.getCourier();
    }

    public Location getLocation() {
        return courierWithLocation.getLocation();
    }

    public PathToDelivery getFirstDelivery() {
        return assignedDeliveries.peek();
    }

    public List<Delivery> getAssignedDeliveries() {
        return assignedDeliveries.stream()
                .map(PathToDelivery::getDelivery)
                .collect(Collectors.toList());
    }

    public Map<Path, List<Delivery>> getPathsFromCourierStartToDeliveryStarts() {
        return pathsFromCourierStartToDeliveryStarts;
    }

}
