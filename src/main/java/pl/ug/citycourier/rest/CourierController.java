package pl.ug.citycourier.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.ug.citycourier.internal.coordinate.CoordinatePairDTO;
import pl.ug.citycourier.internal.courier.CourierCoordinatePairManager;
import pl.ug.citycourier.internal.courier.CourierTask;
import pl.ug.citycourier.internal.courier.CourierTaskManager;

import java.util.Collection;

@RestController("/api/courier")
public class CourierController {

    private CourierTaskManager courierTaskManager;
    private CourierCoordinatePairManager courierCoordinatePairManager;
    private SecurityUserController securityUserController;

    @Autowired
    public CourierController(CourierTaskManager courierTaskManager, SecurityUserController securityUserController, CourierCoordinatePairManager courierCoordinatePairManager) {
        this.courierTaskManager = courierTaskManager;
        this.courierCoordinatePairManager = courierCoordinatePairManager;
        this.securityUserController = securityUserController;
    }

    @GetMapping("/getTasks")
    public ResponseEntity getNewTasksIfNeeded() {
        String username = securityUserController.getUsernameFromContext();
        Collection<CourierTask> tasksForCourier = courierTaskManager.getAndRemoveTasksForCourier(username);
        if (tasksForCourier.isEmpty()) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return ResponseEntity.ok(tasksForCourier);
    }

    @PutMapping("/actualLocation")
    public void updateLocation(@RequestBody CoordinatePairDTO coordinatePairDTO) {
        String username = securityUserController.getUsernameFromContext();
        courierCoordinatePairManager.actualiseCoordinatePairForCourier(username, coordinatePairDTO);
    }

}
