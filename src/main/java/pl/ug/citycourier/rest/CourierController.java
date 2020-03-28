package pl.ug.citycourier.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ug.citycourier.internal.courier.CourierTask;
import pl.ug.citycourier.internal.courier.CourierTaskManager;

import java.util.Collection;

@RestController("/api/courier")
public class CourierController {

    private CourierTaskManager courierTaskManager;
    private SecurityUserController securityUserController;

    @Autowired
    public CourierController(CourierTaskManager courierTaskManager, SecurityUserController securityUserController) {
        this.courierTaskManager = courierTaskManager;
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

}
