package pl.ug.citycourier.internal.algorithm.dto;

import pl.ug.citycourier.internal.algorithm.exception.InternalAlgorithmException;
import pl.ug.citycourier.internal.location.Location;
import pl.ug.citycourier.internal.user.User;

import java.util.*;
import java.util.stream.Collectors;

public class DeliveryAssignerCourier {
    private CourierWithLocation courierWithLocation;
    private Queue<PathToDelivery> assignedDeliveries = new PriorityQueue<>();
    private NavigableMap<Path, List<DeliveryInAlgorithm>> pathsFromCourierStartToDeliveryStarts = new TreeMap<>();

    public DeliveryAssignerCourier(CourierWithLocation courierWithLocation) {
        this.courierWithLocation = courierWithLocation;
    }

    public void assignDelivery(PathToDelivery pathToDelivery) {
        assignedDeliveries.add(pathToDelivery);
    }

    public void addPath(Path path, DeliveryInAlgorithm delivery) {
        pathsFromCourierStartToDeliveryStarts.computeIfAbsent(path, k -> new LinkedList<>());
        pathsFromCourierStartToDeliveryStarts.get(path).add(delivery);
    }

    public PathToDelivery getNearestPathToDelivery() throws InternalAlgorithmException {
        for (Map.Entry<Path, List<DeliveryInAlgorithm>> entry : pathsFromCourierStartToDeliveryStarts.entrySet()) {
            var entryDeliveries = entry.getValue();
            for (int i = 0; i < entryDeliveries.size(); i++) {
                if (entryDeliveries.get(i).isAssigned()) {
                    removePathToDelivery(entry, i);
                } else {
                    return new PathToDelivery(entry.getKey(), entryDeliveries.get(i));
                }
            }
        }
        throw new InternalAlgorithmException("Nearest delivery was not found!");
    }

    public void removePathToDelivery(Map.Entry<Path, List<DeliveryInAlgorithm>> entry, int index) {
        var entryDeliveries = entry.getValue();
        entryDeliveries.remove(index);
        if (entryDeliveries.isEmpty()) {
            pathsFromCourierStartToDeliveryStarts.remove(entry.getKey(), entryDeliveries);
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

    public List<DeliveryInAlgorithm> getAssignedDeliveries() {
        return assignedDeliveries.stream()
                .map(PathToDelivery::getDelivery)
                .collect(Collectors.toList());
    }

    public Map<Path, List<DeliveryInAlgorithm>> getPathsFromCourierStartToDeliveryStarts() {
        return pathsFromCourierStartToDeliveryStarts;
    }

}
