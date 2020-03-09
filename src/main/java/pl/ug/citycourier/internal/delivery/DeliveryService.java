package pl.ug.citycourier.internal.delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ug.citycourier.internal.location.Location;
import pl.ug.citycourier.internal.location.LocationService;
import pl.ug.citycourier.internal.pack.Pack;
import pl.ug.citycourier.internal.pack.PackService;
import pl.ug.citycourier.internal.user.User;
import pl.ug.citycourier.internal.user.UserNotFoundException;
import pl.ug.citycourier.internal.user.UserRepository;

@Service
public class DeliveryService {

    private DeliveryRepository deliveryRepository;
    private UserRepository userRepository;

    private PackService packService;
    private LocationService locationService;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository, UserRepository userRepository, PackService packService, LocationService locationService) {
        this.deliveryRepository = deliveryRepository;
        this.userRepository = userRepository;
        this.packService = packService;
        this.locationService = locationService;
    }

    @Transactional
    public Delivery addDelivery(NewDeliveryDTO newDeliveryDTO, String userName) throws UserNotFoundException {
        Pack pack = packService.createPackFromDTO(newDeliveryDTO.createPackDTO());
        User client = userRepository.findByUsername(userName).orElseThrow(UserNotFoundException::new);
        Location start = locationService.findOrCreateLocationByCoordinate(newDeliveryDTO.getStartCoordinates());
        Location destination = locationService.findOrCreateLocationByCoordinate(newDeliveryDTO.getEndCoordinates());
        Delivery newDelivery = DeliveryBuilder.aDelivery()
                .withClient(client)
                .withPack(pack)
                .withStart(start)
                .withDestination(destination)
                .build();
        return deliveryRepository.save(newDelivery);
    }
}
