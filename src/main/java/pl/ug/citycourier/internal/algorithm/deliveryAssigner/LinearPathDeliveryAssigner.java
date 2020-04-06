package pl.ug.citycourier.internal.algorithm.deliveryAssigner;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import pl.ug.citycourier.internal.algorithm.pathfinder.LinearPathfinder;
import pl.ug.citycourier.internal.delivery.Delivery;
import pl.ug.citycourier.internal.delivery.DeliveryService;
import pl.ug.citycourier.internal.location.Location;
import pl.ug.citycourier.internal.user.User;
import pl.ug.citycourier.internal.user.UserService;

import java.util.*;

// iterate through list of couriers and assign 2 deliveries max to each of them
// based on the shortest path from current courier location (which changes as he delivers packs)
// to next point, be it delivery start or finish

public class LinearPathDeliveryAssigner implements DeliveryAssigner {

    private int CHOICES_FOR_SECOND_DELIVERY_AMOUNT = 3;

    private DeliveryService deliveryService;
    private UserService userService;
    private LinearPathfinder linearPathfinder;

    private ArrayList<Delivery> deliveries = new ArrayList<>();
    private ArrayList<DeliveryAssignerCourier> couriers = new ArrayList<>();

    @Autowired
    public LinearPathDeliveryAssigner(DeliveryService deliveryService, UserService userService,
                                      LinearPathfinder linearPathfinder) {
        this.deliveryService = deliveryService;
        this.userService = userService;
        this.linearPathfinder = linearPathfinder;
    }

    @Override
    public void run() {
        boolean isDataCorrect = prepareData();
        if (isDataCorrect) {
            findPotentialFirstDeliveryDistances();
            assignFirstDeliveries();
            assignSecondDeliveries();
        } else {
            System.out.println("No couriers or deliveries available!");
        }
    }

    private boolean prepareData() {
        ArrayList<User> availableCouriers = (ArrayList<User>) userService.getAvailableCouriers();
        deliveries = (ArrayList<Delivery>) deliveryService.getAvailableDeliveries();
        if (deliveries.isEmpty() || availableCouriers.isEmpty()) {
            return false;
        }
        ArrayList<Location> courierLocations = new ArrayList<>(); //TODO
        couriers = createDeliveryAssignerCouriers(availableCouriers, courierLocations);
        return true;
    }

    private ArrayList<DeliveryAssignerCourier> createDeliveryAssignerCouriers(ArrayList<User> availableCouriers,
                                                                              ArrayList<Location> courierLocations) {
        ArrayList<DeliveryAssignerCourier> couriers = new ArrayList<>();
        for (int i = 0; i < availableCouriers.size(); i++) {
            couriers.add(new DeliveryAssignerCourier(availableCouriers.get(i), courierLocations.get(i)));
        }
        return couriers;
    }

    private void findPotentialFirstDeliveryDistances() {
        for (DeliveryAssignerCourier courier : couriers) {
            for (Delivery delivery : deliveries) {
                Location courierStart = courier.getLocation();
                Location deliveryStart = delivery.getStart();
                double distance = linearPathfinder.findShortestDistanceBetweenTwoLocations(courierStart, deliveryStart);
                courier.addNewDelivery(distance, delivery);
            }
        }
    }

    private void assignFirstDeliveries() {
        ArrayList<Integer> couriersLeftIndices = createListOfIndicesOfAllCouriers();
        do {
            double minDistance = Double.MAX_VALUE;
            Delivery courierFirstDelivery = null;
            int assigneeIndex = -1;
            for (int courierIndex : couriersLeftIndices) {
                if (couriers.get(courierIndex).getDistancesFromCourierStartToDeliveryStarts().isEmpty()) {
                    couriersLeftIndices.remove(assigneeIndex);
                    continue;
                }
                var pair = couriers.get(courierIndex).getNearestDelivery();
                if (pair.getKey() < minDistance) {
                    minDistance = pair.getKey();
                    courierFirstDelivery = pair.getValue();
                    assigneeIndex = courierIndex;
                }
            }
            assert assigneeIndex != -1;
            assert courierFirstDelivery != null;
            couriers.get(assigneeIndex).assignDelivery(minDistance, courierFirstDelivery);
            couriers.get(assigneeIndex).removeNearestDelivery();
            couriersLeftIndices.remove(assigneeIndex);
            removeAlreadyAssignedDelivery(courierFirstDelivery);
        } while (!couriersLeftIndices.isEmpty());
    }

    private ArrayList<Integer> createListOfIndicesOfAllCouriers() {
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < couriers.size(); i++) {
            indices.add(i);
        }
        return indices;
    }

    private void removeAlreadyAssignedDelivery(Delivery delivery) {
        for (var courier : couriers) {
            courier.removeDelivery(delivery);
        }
    }

    private void assignSecondDeliveries() {
        for (var courier : couriers) {
            ArrayList<Pair<Double, Delivery>> potentialSecondDeliveries = choosePotentialSecondDeliveries(courier);
            if (potentialSecondDeliveries.isEmpty()) {
                continue;
            }
            //TODO
//            var pathsToSecondDeliveries
//            var shortestPathsToSecondDeliveries =
//                    chooseShortestPathsToSecondDeliveries(courier, potentialSecondDeliveries);
//            Arrays.sort(shortestPathsToSecondDeliveries);
//            courier.assignDelivery(potentialSecondDeliveries[0]);
        }
    }

    private ArrayList<Pair<Double, Delivery>> choosePotentialSecondDeliveries(DeliveryAssignerCourier courier) {
        ArrayList<Pair<Double, Delivery>> potentialSecondDeliveries = new ArrayList<>();
        var set = courier.getDistancesFromCourierStartToDeliveryStarts().entrySet();
        var it = set.iterator();
        int i = 0;
        while (it.hasNext() || i < CHOICES_FOR_SECOND_DELIVERY_AMOUNT) {
            var entry = it.next();
            var locationAmount = entry.getValue().size();
            if (locationAmount == 1) {
                potentialSecondDeliveries.add(new Pair<>(entry.getKey(), entry.getValue().get(0)));
                i++;
            } else {
                int j = 0;
                while (j < locationAmount && i < CHOICES_FOR_SECOND_DELIVERY_AMOUNT) {
                    potentialSecondDeliveries.add(new Pair<>(entry.getKey(), entry.getValue().get(j)));
                    i++;
                    j++;
                }
            }
        }
        return potentialSecondDeliveries;
    }

    //TODO
//    private double[] chooseShortestPathsToSecondDeliveries(DeliveryAssignerCourier courier, ArrayList<Pair<Double, Delivery>> potentialSecondDeliveries) {
//        double[] shortestPaths = new double[CHOICES_FOR_SECOND_DELIVERY_AMOUNT];
//        for (int i = 0; i < CHOICES_FOR_SECOND_DELIVERY_AMOUNT; i++) {
//
//        }
//    }
}
