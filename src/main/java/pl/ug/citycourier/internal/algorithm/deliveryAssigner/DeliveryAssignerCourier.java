package pl.ug.citycourier.internal.algorithm.deliveryAssigner;

import javafx.util.Pair;
import pl.ug.citycourier.internal.delivery.Delivery;
import pl.ug.citycourier.internal.location.Location;
import pl.ug.citycourier.internal.user.User;

import java.util.*;

public class DeliveryAssignerCourier {
    private User courier;
    private Location location;
    private Collection<Pair<Double, Delivery>> assignedDeliveries = new PriorityQueue<>();
    private TreeMap<Double, LinkedList<Delivery>> distancesFromCourierStartToDeliveryStarts = new TreeMap<>();

    public DeliveryAssignerCourier(User courier, Location location) {
        this.courier = courier;
        this.location = location;
    }

    public void assignDelivery(Double distance, Delivery delivery) {
        assignedDeliveries.add(new Pair<>(distance, delivery));
    }

    public void addNewDelivery(Double distance, Delivery delivery) {
        distancesFromCourierStartToDeliveryStarts.computeIfAbsent(distance, k -> new LinkedList<>());
        distancesFromCourierStartToDeliveryStarts.get(distance).add(delivery);
    }

    public Pair<Double, Delivery> getNearestDelivery() {
        var entry = distancesFromCourierStartToDeliveryStarts.firstEntry();
        return new Pair<>(entry.getKey(), entry.getValue().peek());
    }

    public Pair<Double, Delivery> removeNearestDelivery() {
        var entry = distancesFromCourierStartToDeliveryStarts.firstEntry();
        if (entry.getValue().size() == 1) {
            distancesFromCourierStartToDeliveryStarts.pollFirstEntry();
        } else {
            entry.getValue().poll();
        }
        return new Pair<>(entry.getKey(), entry.getValue().get(0));
    }

    public void removeDelivery(Delivery deliveryToBeRemoved) {
        for (Map.Entry<Double, LinkedList<Delivery>> entry : distancesFromCourierStartToDeliveryStarts.entrySet()) {
            var entryDeliveries = entry.getValue();
            entryDeliveries.remove(deliveryToBeRemoved);
        }
    }

    public User getCourier() {
        return courier;
    }

    public Location getLocation() {
        return location;
    }

    public ArrayList<Delivery> getAssignedDeliveries() {
        ArrayList<Delivery> newDeliveries = new ArrayList<>();
        for (var delivery : assignedDeliveries) {
            newDeliveries.add(delivery.getValue());
        }
        return newDeliveries;
    }

    public TreeMap<Double, LinkedList<Delivery>> getDistancesFromCourierStartToDeliveryStarts() {
        return distancesFromCourierStartToDeliveryStarts;
    }

}
