package pl.ug.citycourier.internal.algorithm.deliveryAssigner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.ug.citycourier.internal.algorithm.courierLocationFinder.CourierLocationFinder;
import pl.ug.citycourier.internal.algorithm.dto.CourierWithLocation;
import pl.ug.citycourier.internal.algorithm.dto.DeliveryAssignerCourier;
import pl.ug.citycourier.internal.algorithm.dto.Path;
import pl.ug.citycourier.internal.algorithm.dto.PathToDelivery;
import pl.ug.citycourier.internal.algorithm.exception.InternalAlgorithmException;
import pl.ug.citycourier.internal.algorithm.pathfinder.LinearPathfinder;
import pl.ug.citycourier.internal.algorithm.pathfinder.Pathfinder;
import pl.ug.citycourier.internal.delivery.Delivery;
import pl.ug.citycourier.internal.delivery.DeliveryService;
import pl.ug.citycourier.internal.location.Location;
import pl.ug.citycourier.internal.user.Status;
import pl.ug.citycourier.internal.user.User;
import pl.ug.citycourier.internal.user.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class BasicDeliveryAssigner implements DeliveryAssigner {

    private final int CHOICES_FOR_SECOND_DELIVERY_AMOUNT = 3;

    private DeliveryService deliveryService;
    private UserService userService;
    private Pathfinder pathfinder;
    private CourierLocationFinder courierLocationFinder;

    private List<Delivery> deliveries = new ArrayList<>();
    private List<DeliveryAssignerCourier> couriers = new ArrayList<>();

    @Autowired
    public BasicDeliveryAssigner(DeliveryService deliveryService, UserService userService,
                                 LinearPathfinder linearPathfinder, CourierLocationFinder courierLocationFinder) {
        this.deliveryService = deliveryService;
        this.userService = userService;
        this.pathfinder = linearPathfinder;
        this.courierLocationFinder = courierLocationFinder;
    }

    /**
     * Assign a maximum of 2 deliveries each courier. Couriers are first assigned first delivery,
     * then second, if applicable. First delivery is assigned based on the shortest path from his current position.
     * Second delivery is assigned based on CHOICES_FOR_SECOND_DELIVERY_AMOUNT other closest delivery starts
     * and one of those deliveries is assigned by calculating every possible path length for each delivery and choosing
     * the minimum of minimums.
     */
    @Override
    public void run() {
        try {
            boolean isDataCorrect = prepareData();
            if (isDataCorrect) {
                findDistancesBetweenAllCouriersAndAllDeliveries();
                assignFirstDeliveries();
                assignSecondDeliveries();
            } else {
                System.out.println("No couriers or deliveries available!");
            }
        } catch (InternalAlgorithmException e) {
            System.err.println(e.getMessage());
        }
    }

    private boolean prepareData() {
        List<User> availableCouriers = userService.findCouriersByStatus(Status.AVAILABLE);
        deliveries = deliveryService.getAvailableDeliveries();
        if (deliveries.isEmpty() || availableCouriers.isEmpty()) {
            return false;
        }
        List<Location> courierLocations = courierLocationFinder.findAllCouriersLocation();
        couriers = createDeliveryAssignerCouriers(availableCouriers, courierLocations);
        return true;
    }

    private void findDistancesBetweenAllCouriersAndAllDeliveries() {
        for (DeliveryAssignerCourier courier : couriers) {
            for (Delivery delivery : deliveries) {
                Location courierStart = courier.getLocation();
                Location deliveryStart = delivery.getStart();
                Path path = pathfinder.findShortestPath(courierStart, deliveryStart);
                courier.addPath(path, delivery);
            }
        }
    }

    private List<DeliveryAssignerCourier> createDeliveryAssignerCouriers(List<User> availableCouriers,
                                                                         List<Location> courierLocations) {
        ArrayList<DeliveryAssignerCourier> couriers = new ArrayList<>();
        for (int i = 0; i < availableCouriers.size(); i++) {
            var courierWithLocation = new CourierWithLocation(availableCouriers.get(i), courierLocations.get(i));
            var deliveryAssignerCourier = new DeliveryAssignerCourier(courierWithLocation);
            couriers.add(deliveryAssignerCourier);
        }
        return couriers;
    }

    private void assignFirstDeliveries() throws InternalAlgorithmException {
        List<Integer> couriersLeftIndices =
                IntStream.range(0, couriers.size()).boxed().collect(Collectors.toCollection(ArrayList::new));
        do {
            Path minPath = new Path(Double.MAX_VALUE);
            Delivery courierFirstDelivery = null;
            Integer assigneeIndex = null;
            for (int courierIndex : couriersLeftIndices) {
                if (doesCourierHaveEmptyList(courierIndex)) {
                    couriersLeftIndices.remove(assigneeIndex);
                } else {
                    var pair = couriers.get(courierIndex).getNearestDelivery();
                    if (pair.getPath().equals(minPath)) {
                        minPath = pair.getPath();
                        courierFirstDelivery = pair.getDelivery();
                        assigneeIndex = courierIndex;
                    }
                }
            }
            assertResults(assigneeIndex, courierFirstDelivery);
            couriers.get(assigneeIndex).assignDelivery(new PathToDelivery(minPath, courierFirstDelivery));
            removeAlreadyAssignedDelivery(courierFirstDelivery);
            couriersLeftIndices.remove(assigneeIndex);
        } while (!couriersLeftIndices.isEmpty());
    }

    private boolean doesCourierHaveEmptyList(int courierIndex) {
        return couriers.get(courierIndex).getPathsFromCourierStartToDeliveryStarts().isEmpty();
    }

    private void assertResults(Integer assigneeIndex, Delivery courierFirstDelivery) throws InternalAlgorithmException {
        if (assigneeIndex == null || courierFirstDelivery == null) {
            throw new InternalAlgorithmException("Invalid assignee index");
        }
    }

    private void removeAlreadyAssignedDelivery(Delivery delivery) {
        for (var courier : couriers) {
            courier.removeDelivery(delivery);
        }
    }

    private void assignSecondDeliveries() {
        for (var courier : couriers) {
            ArrayList<PathToDelivery> potentialSecondDeliveries = choosePotentialSecondDeliveries(courier);
            if (!potentialSecondDeliveries.isEmpty()) {
                var shortestPathsToSecondDeliveries =
                        chooseShortestPathsToSecondDeliveries(courier, potentialSecondDeliveries);
                var courierSecondDelivery =
                        Collections.min(shortestPathsToSecondDeliveries, Comparator.comparing(PathToDelivery::getPath));
                courier.assignDelivery(courierSecondDelivery);
                removeAlreadyAssignedDelivery(courierSecondDelivery.getDelivery());
            }
        }
    }

    private ArrayList<PathToDelivery> choosePotentialSecondDeliveries(DeliveryAssignerCourier courier) {
        ArrayList<PathToDelivery> potentialSecondDeliveries = new ArrayList<>();
        var set = courier.getPathsFromCourierStartToDeliveryStarts().entrySet();
        var it = set.iterator();
        int i = 0;
        while (it.hasNext() || i < CHOICES_FOR_SECOND_DELIVERY_AMOUNT) {
            var entry = it.next();
            var locationAmount = entry.getValue().size();
            if (locationAmount == 1) {
                potentialSecondDeliveries.add(new PathToDelivery(entry.getKey(), entry.getValue().get(0)));
                i++;
            } else {
                int j = 0;
                while (j < locationAmount && i < CHOICES_FOR_SECOND_DELIVERY_AMOUNT) {
                    potentialSecondDeliveries.add(new PathToDelivery(entry.getKey(), entry.getValue().get(j)));
                    i++;
                    j++;
                }
            }
        }
        return potentialSecondDeliveries;
    }

    private ArrayList<PathToDelivery> chooseShortestPathsToSecondDeliveries(
            DeliveryAssignerCourier courier, ArrayList<PathToDelivery> potentialSecondDeliveries) {
        ArrayList<PathToDelivery> shortestPathsToSecondDeliveries = new ArrayList<>();
        for (var delivery : potentialSecondDeliveries) {
            var sixPathsDistances = computeSixPathsDistances(courier, delivery);
            var shortestPath = Collections.min(sixPathsDistances);
            shortestPathsToSecondDeliveries.add(new PathToDelivery(shortestPath, delivery.getDelivery()));
        }
        return shortestPathsToSecondDeliveries;
    }

    // CS - courier start
    // AS - delivery A start
    // AF - delivery A finish
    // BS - delivery B start
    // BF - delivery B finish
    private ArrayList<Path> computeSixPathsDistances(
            DeliveryAssignerCourier courier, PathToDelivery pathToDelivery) {
        ArrayList<Path> paths = new ArrayList<>();
        var CS = courier.getLocation();
        var BS = pathToDelivery.getDelivery().getStart();
        var BF = pathToDelivery.getDelivery().getDestination();
        var AS = courier.getFirstDelivery().getDelivery().getStart();
        var AF = courier.getFirstDelivery().getDelivery().getDestination();

        var CS_AS = courier.getFirstDelivery().getPath();
        var AS_AF = pathfinder.findShortestPath(AS, AF);
        var AF_BS = pathfinder.findShortestPath(AF, BS);
        var BS_BF = pathfinder.findShortestPath(BS, BF);
        paths.add(pathfinder.sumPaths(CS_AS, AS_AF, AF_BS, BS_BF));

        var AS_BS = pathfinder.findShortestPath(AS, BS);
        var BF_AF = pathfinder.findShortestPath(BS, BF);
        paths.add(pathfinder.sumPaths(CS_AS, AS_BS, BS_BF, BF_AF));

        var BS_AF = pathfinder.findShortestPath(BS, AF);
        var AF_BF = pathfinder.findShortestPath(AF, BF);
        paths.add(pathfinder.sumPaths(CS_AS, AS_BS, BS_AF, AF_BF));

        var CS_BS = pathfinder.findShortestPath(CS, BS);
        var BF_AS = pathfinder.findShortestPath(BF, AS);
        paths.add(pathfinder.sumPaths(CS_BS, BS_BF, BF_AS, AS_AF));

        var BS_AS = pathfinder.findShortestPath(BS, AS);
        paths.add(pathfinder.sumPaths(CS_BS, BS_AS, AS_AF, AF_BF));

        var AS_BF = pathfinder.findShortestPath(AS, BF);
        paths.add(pathfinder.sumPaths(CS_BS, BS_AS, AS_BF, BF_AF));

        return paths;
    }

}
