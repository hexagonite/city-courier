package pl.ug.citycourier.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.ug.citycourier.internal.common.EntityNotFoundException;
import pl.ug.citycourier.internal.delivery.Delivery;
import pl.ug.citycourier.internal.delivery.DeliveryService;
import pl.ug.citycourier.internal.delivery.NewDeliveryDTO;
import pl.ug.citycourier.internal.user.User;
import pl.ug.citycourier.internal.user.UserNotFoundException;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {

    private DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping("/addPack")
    public Delivery addNewDelivery(@RequestBody NewDeliveryDTO newDeliveryDTO) throws UserNotFoundException {
        return deliveryService.addDelivery(newDeliveryDTO, getUserNameFromSecurityContextHolder());
    }

    @GetMapping("/getPackFromClient/{id}")
    public void getPackFromClient(@PathVariable("id") long packId) throws EntityNotFoundException {
        deliveryService.getPackFromClient(packId, getUserNameFromSecurityContextHolder());
    }

    private String getUserNameFromSecurityContextHolder() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else if (principal instanceof User) {
            return ((User) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
