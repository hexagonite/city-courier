package pl.ug.citycourier.internal.algorithm.dto;

import pl.ug.citycourier.internal.algorithm.exception.InternalAlgorithmException;
import pl.ug.citycourier.internal.coordinate.CoordinatePairDTO;
import pl.ug.citycourier.internal.location.Location;
import pl.ug.citycourier.internal.user.User;

import java.util.*;
import java.util.stream.Collectors;

public class CourierInAlgorithm {
    private CourierWithCoordinates courierWithCoordinates;
    private ShortestCourierPath shortestCourierPath;
    private Queue<PathToDelivery> assignedDeliveries = new PriorityQueue<>();
    private NavigableMap<Path, List<DeliveryInAlgorithm>> pathsFromCourierStartToDeliveryStarts = new TreeMap<>();

    public CourierInAlgorithm(CourierWithCoordinates courierWithCoordinates) {
        this.courierWithCoordinates = courierWithCoordinates;
    }

    public void assignDelivery(PathToDelivery pathToDelivery) {
        assignedDeliveries.add(pathToDelivery);
    }

    public void addPath(Path path, DeliveryInAlgorithm delivery) {
        pathsFromCourierStartToDeliveryStarts.computeIfAbsent(path, k -> new LinkedList<>());
        pathsFromCourierStartToDeliveryStarts.get(path).add(delivery);
    }

    public PathToDelivery getNearestPathToDeliveryWithRemoval() throws InternalAlgorithmException {
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

    public List<PathToDelivery> getNearestPathsToDeliveriesWithRemoval(int maxResults) {
        List<PathToDelivery> pathsToDeliveries = new ArrayList<>();
        var set = getPathsFromCourierStartToDeliveryStarts().entrySet();
        var it = set.iterator();
        int i = 0;
        while (it.hasNext() || i < maxResults) {
            var entry = it.next();
            var locationAmount = entry.getValue().size();
            int listIndex = 0;
            while (listIndex < locationAmount && i < maxResults) {
                if (entry.getValue().get(i).isAssigned()) {
                    removePathToDelivery(entry, listIndex);
                } else {
                    pathsToDeliveries.add(new PathToDelivery(entry.getKey(), entry.getValue().get(listIndex)));
                    i++;
                }
                listIndex++;
            }
        }
        return pathsToDeliveries;
    }

    private void removePathToDelivery(Map.Entry<Path, List<DeliveryInAlgorithm>> entry, int index) {
        var entryDeliveries = entry.getValue();
        entryDeliveries.remove(index);
        if (entryDeliveries.isEmpty()) {
            pathsFromCourierStartToDeliveryStarts.remove(entry.getKey(), entryDeliveries);
        }
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

    public Map<Path, List<DeliveryInAlgorithm>> getPathsFromCourierStartToDeliveryStarts() {
        return pathsFromCourierStartToDeliveryStarts;
    }

}
