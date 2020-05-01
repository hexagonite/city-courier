package pl.ug.citycourier.internal.algorithm.dto;

import pl.ug.citycourier.internal.algorithm.exception.InternalAlgorithmException;
import pl.ug.citycourier.internal.coordinate.CoordinatePairDTO;
import pl.ug.citycourier.internal.user.User;

import java.util.*;
import java.util.stream.Collectors;

public class CourierInAlgorithm {
    private CourierWithCoordinates courierWithCoordinates;
    private ShortestCourierPath shortestCourierPath;
    private Queue<PathToDelivery> assignedDeliveries = new PriorityQueue<>();
    private List<CourierToDeliveryStartPath> courierToDeliveryStartPaths = new LinkedList<>();

    public CourierInAlgorithm(CourierWithCoordinates courierWithCoordinates) {
        this.courierWithCoordinates = courierWithCoordinates;
    }

    public void assignDelivery(PathToDelivery pathToDelivery) {
        assignedDeliveries.add(pathToDelivery);
    }

    public void addPath(Path path, DeliveryInAlgorithm delivery) {
        courierToDeliveryStartPaths.add(new CourierToDeliveryStartPath(path, delivery));
    }

    public void sortPaths() {
        Collections.sort(courierToDeliveryStartPaths);
    }

    public PathToDelivery getNearestPathToDeliveryWithRemoval() throws InternalAlgorithmException {
        for (var courierToDeliveryStartPath : courierToDeliveryStartPaths) {
            if (courierToDeliveryStartPath.isDeliveryAssigned()) {
                courierToDeliveryStartPaths.remove(0);
            } else {
                return new PathToDelivery(courierToDeliveryStartPath);
            }
        }
        throw new InternalAlgorithmException("Nearest delivery was not found!");
    }

    public List<PathToDelivery> getNearestPathsToDeliveriesWithRemoval(int maxResults) {
        List<PathToDelivery> pathsToDeliveries = new ArrayList<>();
        for (var courierToDeliveryStartPath : courierToDeliveryStartPaths) {
            if (courierToDeliveryStartPath.isDeliveryAssigned()) {
                courierToDeliveryStartPaths.remove(0);
            } else {
                pathsToDeliveries.add(new PathToDelivery(courierToDeliveryStartPath));
                if (pathsToDeliveries.size() == 3) {
                    break;
                }
            }
        }
        return pathsToDeliveries;
    }

    public User getCourier() {
        return courierWithCoordinates.getCourier();
    }

    public CoordinatePairDTO getCoordinates() {
        return courierWithCoordinates.getCoordinatePairDTO();
    }

    public PathToDelivery getFirstDelivery() {
        return assignedDeliveries.peek();
    }

    public ShortestCourierPath getShortestCourierPath() {
        return shortestCourierPath;
    }

    public void setShortestCourierPath(ShortestCourierPath shortestCourierPath) {
        this.shortestCourierPath = shortestCourierPath;
    }

    public List<DeliveryInAlgorithm> getAssignedDeliveries() {
        return assignedDeliveries.stream()
                .map(PathToDelivery::getDeliveryInAlgorithm)
                .collect(Collectors.toList());
    }

    public List<CourierToDeliveryStartPath> getCourierToDeliveryStartPaths() {
        return courierToDeliveryStartPaths;
    }

}
