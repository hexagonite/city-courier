package pl.ug.citycourier.internal.algorithm.deliveryAssigner;

import org.springframework.beans.factory.annotation.Autowired;
import pl.ug.citycourier.internal.coordinate.CoordinatePair;
import pl.ug.citycourier.internal.delivery.Delivery;
import pl.ug.citycourier.internal.delivery.DeliveryService;
import pl.ug.citycourier.internal.location.Location;
import pl.ug.citycourier.internal.user.User;
import pl.ug.citycourier.internal.user.UserService;

import java.util.ArrayList;

public class LinearPathDeliveryAssigner implements DeliveryAssigner{

    private DeliveryService deliveryService;
    private UserService userService;

    @Autowired
    public LinearPathDeliveryAssigner(DeliveryService deliveryService, UserService userService) {
        this.deliveryService = deliveryService;
        this.userService = userService;
    }

    @Override
    public void run() {
        ArrayList<Delivery> deliveries = (ArrayList<Delivery>) deliveryService.getAvailableDeliveries();
        ArrayList<User> couriers = (ArrayList<User>) userService.getAvailableCouriers();
        ArrayList<Location> courierLocations = new ArrayList<>(); // TODO
        courierLocations.add(new Location()); // stub / temp

        if (deliveries.isEmpty() || couriers.isEmpty()) {
            System.out.println("No couriers or deliveries available!");
            return;
        }

        while (!deliveries.isEmpty()) {
            double min = Double.MAX_VALUE;
            for (int i = 0; i < deliveries.size(); i++) {
                Location start = courierLocations.get(i);
                //Location finish = deliveries;

            }
        }

    }

}
