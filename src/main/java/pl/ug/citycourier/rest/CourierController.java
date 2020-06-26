package pl.ug.citycourier.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ug.citycourier.internal.coordinate.CoordinatePairDTO;
import pl.ug.citycourier.internal.courier.CourierCoordinatePairManager;
import pl.ug.citycourier.internal.courier.CourierTask;
import pl.ug.citycourier.internal.courier.CourierTaskManager;
import pl.ug.citycourier.internal.user.UserNotFoundException;
import pl.ug.citycourier.internal.user.UserService;
import pl.ug.citycourier.internal.user.UserStatusWrapper;

import java.util.Collection;

@RestController
@RequestMapping("/api/courier")
public class CourierController {

    private CourierTaskManager courierTaskManager;
    private CourierCoordinatePairManager courierCoordinatePairManager;
    private SecurityUserController securityUserController;
    private UserService userService;

    @Autowired
    public CourierController(CourierTaskManager courierTaskManager, CourierCoordinatePairManager courierCoordinatePairManager, SecurityUserController securityUserController, UserService userService) {
        this.courierTaskManager = courierTaskManager;
        this.courierCoordinatePairManager = courierCoordinatePairManager;
        this.securityUserController = securityUserController;
        this.userService = userService;
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

    @PutMapping("/updateLocation")
    public void updateLocation(@RequestBody CoordinatePairDTO coordinatePairDTO) {
        String username = securityUserController.getUsernameFromContext();
        courierCoordinatePairManager.updateCourierCoordinatePair(username, coordinatePairDTO);
    }

    @PutMapping("/updateStatus")
    public void updateStatus(@RequestBody UserStatusWrapper userStatusWrapper) throws UserNotFoundException {
        String username = securityUserController.getUsernameFromContext();
        userService.updateStatus(username, userStatusWrapper.getStatus());
    }

}
