package pl.ug.citycourier.internal.delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ug.citycourier.internal.common.EntityNotFoundException;
import pl.ug.citycourier.internal.location.Location;
import pl.ug.citycourier.internal.location.LocationService;
import pl.ug.citycourier.internal.pack.Pack;
import pl.ug.citycourier.internal.pack.PackNotFoundException;
import pl.ug.citycourier.internal.pack.PackRepository;
import pl.ug.citycourier.internal.pack.PackService;
import pl.ug.citycourier.internal.security.boundary.RoleName;
import pl.ug.citycourier.internal.user.User;
import pl.ug.citycourier.internal.user.UserNotFoundException;
import pl.ug.citycourier.internal.user.UserRepository;

import java.time.LocalDateTime;

@Service
public class DeliveryService {

    private DeliveryRepository deliveryRepository;
    private UserRepository userRepository;
    private PackRepository packRepository;

    private PackService packService;
    private LocationService locationService;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository, UserRepository userRepository,
                           PackService packService, LocationService locationService, PackRepository packRepository) {
        this.deliveryRepository = deliveryRepository;
        this.userRepository = userRepository;
        this.packService = packService;
        this.locationService = locationService;
        this.packRepository = packRepository;
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

    @Transactional
    public void getPackFromClient(long packId, String userName) throws EntityNotFoundException {
        User courier = getCourierFromUsername(userName);
        Delivery delivery = getDeliveryByPackId(packId);
        delivery.setCourier(courier);
        delivery.setGetPackDate(LocalDateTime.now());
    }

    private User getCourierFromUsername(String userName) throws EntityNotFoundException {
        User courier = userRepository.findByUsername(userName).orElseThrow(UserNotFoundException::new);
        if (courier.getRole().getName() == RoleName.COURIER) {
            return courier;
        }
        throw new BadCredentialsException("You need to be courier to get pack from client");
    }

    private Delivery getDeliveryByPackId(long packId) throws EntityNotFoundException {
        Pack pack = packRepository.findById(packId).orElseThrow(PackNotFoundException::new);
        return deliveryRepository.findByPack(pack).orElseThrow(DeliveryNotFoundException::new);
    }

    @Transactional
    public void deliverPack(long packId, String username) throws EntityNotFoundException {
        checkIfUserIsCourier(username);
        Delivery delivery = getDeliveryByPackId(packId);
        delivery.setDeliverPackDate(LocalDateTime.now());
    }

    private void checkIfUserIsCourier(String username) throws EntityNotFoundException {
        try {
            getCourierFromUsername(username);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("You need to be courier to deliver pack to client");
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }
}
