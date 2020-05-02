package pl.ug.citycourier.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.ug.citycourier.internal.common.EntityNotFoundException;
import pl.ug.citycourier.internal.delivery.Delivery;
import pl.ug.citycourier.internal.delivery.DeliveryService;
import pl.ug.citycourier.internal.delivery.NewDeliveryDTO;
import pl.ug.citycourier.internal.user.UserNotFoundException;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {

    private DeliveryService deliveryService;
    private SecurityUserController securityUserController;

    @Autowired
    public DeliveryController(DeliveryService deliveryService, SecurityUserController securityUserController) {
        this.deliveryService = deliveryService;
        this.securityUserController = securityUserController;
    }

    @PostMapping("/addPack")
    public Delivery addNewDelivery(@RequestBody NewDeliveryDTO newDeliveryDTO) throws UserNotFoundException {
        return deliveryService.addDelivery(newDeliveryDTO, securityUserController.getUsernameFromContext());
    }

    @GetMapping("/getPackFromClient/{id}")
    public void getPackFromClient(@PathVariable("id") long packId) throws EntityNotFoundException {
        deliveryService.getPackFromClient(packId, securityUserController.getUsernameFromContext());
    }

    @GetMapping("/deliverPack/{id}")
    public void deliverPack(@PathVariable("id") long packId) throws EntityNotFoundException {
        deliveryService.deliverPack(packId, securityUserController.getUsernameFromContext());
    }
}
