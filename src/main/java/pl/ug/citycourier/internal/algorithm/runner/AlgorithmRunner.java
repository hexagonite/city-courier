package pl.ug.citycourier.internal.algorithm.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.ug.citycourier.internal.algorithm.deliveryAssignerAlgorithm.DeliveryAssigner;
import pl.ug.citycourier.internal.algorithm.dto.CourierInAlgorithm;
import pl.ug.citycourier.internal.algorithm.dto.CourierWithCoordinates;
import pl.ug.citycourier.internal.algorithm.dto.DeliveryInAlgorithm;
import pl.ug.citycourier.internal.algorithm.exception.InternalAlgorithmException;
import pl.ug.citycourier.internal.courier.CourierCoordinatePairManager;
import pl.ug.citycourier.internal.courier.CourierTaskManager;
import pl.ug.citycourier.internal.delivery.DeliveryNotFoundException;
import pl.ug.citycourier.internal.delivery.DeliveryService;
import pl.ug.citycourier.internal.user.Status;
import pl.ug.citycourier.internal.user.UserNotFoundException;
import pl.ug.citycourier.internal.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlgorithmRunner {
    private DeliveryAssigner deliveryAssigner;
    private UserService userService;
    private DeliveryService deliveryService;
    private CourierCoordinatePairManager courierCoordinatePairManager;
    private CourierTaskManager courierTaskManager;

    @Autowired
    public AlgorithmRunner(DeliveryAssigner deliveryAssigner,
                           UserService userService,
                           DeliveryService deliveryService,
                           CourierCoordinatePairManager courierCoordinatePairManager,
                           CourierTaskManager courierTaskManager) {
        this.deliveryAssigner = deliveryAssigner;
        this.userService = userService;
        this.deliveryService = deliveryService;
        this.courierCoordinatePairManager = courierCoordinatePairManager;
        this.courierTaskManager = courierTaskManager;
    }

    @Scheduled(initialDelayString = "${initialDelay.in.milliseconds}", fixedDelayString = "${fixedDelay.in.milliseconds}")
    public void run() {
        var availableDeliveries = getAvailableDeliveries();
        if (!availableDeliveries.isEmpty()) {
            var availableCouriers = getAvailableCouriers();
            if (!availableCouriers.isEmpty()) {
                assignDeliveries(availableDeliveries, availableCouriers);
            }
        }
    }

    private List<DeliveryInAlgorithm> getAvailableDeliveries() {
        return deliveryService.getAvailableDeliveries()
                .stream()
                .map(DeliveryInAlgorithm::new)
                .collect(Collectors.toList());
    }

    private List<CourierInAlgorithm> getAvailableCouriers() {
        return userService.findCouriersByStatus(Status.AVAILABLE)
                .stream()
                .map(courier -> new CourierInAlgorithm(new CourierWithCoordinates(courier,
                        courierCoordinatePairManager.getCoordinatePairForCourierName(courier.getName()))))
                .collect(Collectors.toList());
    }

    private void assignDeliveries(List<DeliveryInAlgorithm> availableDeliveries, List<CourierInAlgorithm> availableCouriers) {
        try {
            var courierJobs = deliveryAssigner.run(availableDeliveries, availableCouriers);
            for (var courierJob : courierJobs) {
                for (var courierTask : courierJob.getTasks()) {
                    courierTaskManager.addNewTaskForCourier(courierJob.getCourier().getName(), courierTask);
                }
            }
        } catch (InternalAlgorithmException | UserNotFoundException | DeliveryNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

}
