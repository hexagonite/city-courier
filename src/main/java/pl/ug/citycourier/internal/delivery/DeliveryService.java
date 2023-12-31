package pl.ug.citycourier.internal.delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ug.citycourier.internal.common.EntityNotFoundException;
import pl.ug.citycourier.internal.location.Location;
import pl.ug.citycourier.internal.location.LocationService;
import pl.ug.citycourier.internal.pack.Pack;
import pl.ug.citycourier.internal.pack.PackNotFoundException;
import pl.ug.citycourier.internal.pack.PackRepository;
import pl.ug.citycourier.internal.pack.PackService;
import pl.ug.citycourier.internal.user.User;
import pl.ug.citycourier.internal.user.UserNotFoundException;
import pl.ug.citycourier.internal.user.UserRepository;
import pl.ug.citycourier.internal.user.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService {

    private DeliveryRepository deliveryRepository;
    private UserRepository userRepository;
    private PackRepository packRepository;

    private PackService packService;
    private LocationService locationService;
    private UserService userService;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository, UserRepository userRepository,
                           PackService packService, LocationService locationService, PackRepository packRepository,
                           UserService userService) {
        this.deliveryRepository = deliveryRepository;
        this.userRepository = userRepository;
        this.packService = packService;
        this.locationService = locationService;
        this.packRepository = packRepository;
        this.userService = userService;
    }

    @Transactional
    public Delivery addDelivery(NewDeliveryDTO newDeliveryDTO, String userName) throws UserNotFoundException {
        Pack pack = packService.createPackFromDTO(newDeliveryDTO.createPackDTO());
        User client = userService.getUserFromUsername(userName);
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

    public void assignDeliveryToCourier(Delivery delivery, User courier) throws DeliveryNotFoundException {
        Delivery databaseDelivery = findById(delivery.getId());
        databaseDelivery.setCourier(courier);
        deliveryRepository.save(databaseDelivery);
    }

    public Delivery findById(Long id) throws DeliveryNotFoundException {
        return deliveryRepository.findById(id).orElseThrow(DeliveryNotFoundException::new);
    }

    @Transactional
    public void getPackFromClient(long packId, String userName) throws EntityNotFoundException {
        User courier = userService.getCourierFromUsername(userName);
        Delivery delivery = getDeliveryByPackId(packId);
        delivery.setCourier(courier);
        delivery.setReceivedAt(LocalDateTime.now());
    }

    private Delivery getDeliveryByPackId(long packId) throws EntityNotFoundException {
        Pack pack = packRepository.findById(packId).orElseThrow(PackNotFoundException::new);
        return deliveryRepository.findByPack(pack).orElseThrow(DeliveryNotFoundException::new);
    }

    @Transactional
    public void deliverPack(long packId, String username) throws EntityNotFoundException {
        Delivery delivery = getDeliveryByPackId(packId);
        String assignedCourierName = getAssignedCourierName(delivery);
        validateCourier(username, assignedCourierName);
        delivery.setDeliveredAt(LocalDateTime.now());
    }

    private String getAssignedCourierName(Delivery delivery) throws EntityNotFoundException {
        return Optional.ofNullable(delivery.getCourier())
                .map(User::getUsername)
                .orElseThrow(() -> new EntityNotFoundException("There is no assigned courier for this pack!"));
    }

    private void validateCourier(String username, String assignedCourierName) {
        if (!username.equals(assignedCourierName)) {
            throw new SecurityException("This courier cannot deliver this pack!");
        }
    }

    public List<Delivery> getAvailableDeliveries() {
        return deliveryRepository.findByCourierIsNull();
    }
}
