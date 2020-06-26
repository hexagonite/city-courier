package pl.ug.citycourier.internal.delivery;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import pl.ug.citycourier.internal.common.EntityNotFoundException;
import pl.ug.citycourier.internal.coordinate.CoordinatePairDTO;
import pl.ug.citycourier.internal.location.Location;
import pl.ug.citycourier.internal.location.LocationService;
import pl.ug.citycourier.internal.pack.Pack;
import pl.ug.citycourier.internal.pack.PackDTO;
import pl.ug.citycourier.internal.pack.PackNotFoundException;
import pl.ug.citycourier.internal.pack.PackRepository;
import pl.ug.citycourier.internal.pack.PackService;
import pl.ug.citycourier.internal.user.User;
import pl.ug.citycourier.internal.user.UserNotFoundException;
import pl.ug.citycourier.internal.user.UserService;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {

    @InjectMocks
    private DeliveryService testee;

    @Mock
    private DeliveryRepository deliveryRepository;
    @Mock
    private PackRepository packRepository;
    @Mock
    private PackService packService;
    @Mock
    private LocationService locationService;
    @Mock
    private UserService userService;

    @Mock
    private NewDeliveryDTO newDeliveryDTO;
    @Mock
    private PackDTO packDTO;
    @Mock
    private CoordinatePairDTO coordinatePairDTO1;
    @Mock
    private CoordinatePairDTO coordinatePairDTO2;
    @Mock
    private User user;
    @Mock
    private Pack pack;
    @Mock
    private Delivery delivery;
    @Mock
    private Location location1;
    @Mock
    private Location location2;

    @Test
    void addDelivery() throws UserNotFoundException {
        String userName = "name";

        when(newDeliveryDTO.createPackDTO()).thenReturn(packDTO);
        when(newDeliveryDTO.getStartCoordinates()).thenReturn(coordinatePairDTO1);
        when(newDeliveryDTO.getEndCoordinates()).thenReturn(coordinatePairDTO2);
        when(packService.createPackFromDTO(packDTO)).thenReturn(pack);
        when(locationService.findOrCreateLocationByCoordinate(coordinatePairDTO1)).thenReturn(location1);
        when(locationService.findOrCreateLocationByCoordinate(coordinatePairDTO2)).thenReturn(location2);
        when(userService.getUserFromUsername(userName)).thenReturn(user);

        testee.addDelivery(newDeliveryDTO, userName);

        verify(deliveryRepository).save(any(Delivery.class));
    }

    @Test
    void addDelivery_userNotFound_shouldThrowException() throws UserNotFoundException {
        String userName = "name";

        when(newDeliveryDTO.createPackDTO()).thenReturn(packDTO);
        when(packService.createPackFromDTO(packDTO)).thenReturn(pack);
        when(userService.getUserFromUsername(userName)).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class,
                () -> testee.addDelivery(newDeliveryDTO, userName));

        verifyNoInteractions(deliveryRepository);
    }

    @Test
    void getPackFromClient() throws EntityNotFoundException {
        long packId = 1L;
        String userName = "name";

        when(userService.getCourierFromUsername(userName)).thenReturn(user);
        when(packRepository.findById(packId)).thenReturn(Optional.of(pack));
        when(deliveryRepository.findByPack(pack)).thenReturn(Optional.of(delivery));

        testee.getPackFromClient(packId, userName);
        verify(delivery).setCourier(user);
        verify(delivery).setReceivedAt(any(LocalDateTime.class));
    }


    @Test
    void getPackFromClient_courierNotFound() throws UserNotFoundException {
        long packId = 1L;
        String userName = "name";

        when(userService.getCourierFromUsername(userName)).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class,
                () -> testee.getPackFromClient(packId, userName));

        verify(delivery, times(0)).setCourier(user);
        verify(delivery, times(0)).setReceivedAt(any(LocalDateTime.class));
    }

    @Test
    void getPackFromClient_userIsNotACourier() throws UserNotFoundException {
        long packId = 1L;
        String userName = "name";

        when(userService.getCourierFromUsername(userName)).thenThrow(BadCredentialsException.class);

        assertThrows(BadCredentialsException.class,
                () -> testee.getPackFromClient(packId, userName));

        verify(delivery, times(0)).setCourier(user);
        verify(delivery, times(0)).setReceivedAt(any(LocalDateTime.class));
    }


    @Test
    void getPackFromClient_packNotFound() throws UserNotFoundException {
        long packId = 1L;
        String userName = "name";

        when(userService.getCourierFromUsername(userName)).thenReturn(user);
        when(packRepository.findById(packId)).thenReturn(Optional.empty());

        assertThrows(PackNotFoundException.class,
                () -> testee.getPackFromClient(packId, userName));

        verify(delivery, times(0)).setCourier(user);
        verify(delivery, times(0)).setReceivedAt(any(LocalDateTime.class));
    }

    @Test
    void getPackFromClient_deliveryNotFound() throws UserNotFoundException {
        long packId = 1L;
        String userName = "name";

        when(userService.getCourierFromUsername(userName)).thenReturn(user);
        when(packRepository.findById(packId)).thenReturn(Optional.of(pack));
        when(deliveryRepository.findByPack(pack)).thenReturn(Optional.empty());

        assertThrows(DeliveryNotFoundException.class,
                () -> testee.getPackFromClient(packId, userName));

        verify(delivery, times(0)).setCourier(user);
        verify(delivery, times(0)).setReceivedAt(any(LocalDateTime.class));
    }


    @Test
    void deliverPack() throws EntityNotFoundException {
        long packId = 1L;
        String userName = "name";

        when(packRepository.findById(packId)).thenReturn(Optional.of(pack));
        when(delivery.getCourier()).thenReturn(user);
        when(user.getUsername()).thenReturn(userName);
        when(deliveryRepository.findByPack(pack)).thenReturn(Optional.of(delivery));

        testee.deliverPack(packId, userName);

        verify(delivery).setDeliveredAt(any(LocalDateTime.class));
    }


    @Test
    void deliverPack_courierNotAssignedToPack() throws UserNotFoundException {
        long packId = 1L;
        String userName = "name";

        when(packRepository.findById(packId)).thenReturn(Optional.of(pack));
        when(delivery.getCourier()).thenReturn(null);
        when(deliveryRepository.findByPack(pack)).thenReturn(Optional.of(delivery));

        assertThrows(EntityNotFoundException.class,
                () -> testee.deliverPack(packId, userName));

        verify(delivery, times(0)).setDeliveredAt(any(LocalDateTime.class));
    }

    @Test
    void deliverPack_courierDeliveringIsNotTheSameAsCourierAssigned() throws UserNotFoundException {
        long packId = 1L;
        String userName = "name";

        when(packRepository.findById(packId)).thenReturn(Optional.of(pack));
        when(delivery.getCourier()).thenReturn(user);
        when(user.getUsername()).thenReturn("badName");
        when(deliveryRepository.findByPack(pack)).thenReturn(Optional.of(delivery));

        assertThrows(SecurityException.class,
                () -> testee.deliverPack(packId, userName));

        verify(delivery, times(0)).setDeliveredAt(any(LocalDateTime.class));
    }


    @Test
    void deliverPack_packNotFound() {
        long packId = 1L;
        String userName = "name";

        when(packRepository.findById(packId)).thenReturn(Optional.empty());

        assertThrows(PackNotFoundException.class,
                () -> testee.deliverPack(packId, userName));

        verify(delivery, times(0)).setDeliveredAt(any(LocalDateTime.class));
    }

    @Test
    void deliverPack_deliveryNotFound() {
        long packId = 1L;
        String userName = "name";

        when(packRepository.findById(packId)).thenReturn(Optional.of(pack));
        when(deliveryRepository.findByPack(pack)).thenReturn(Optional.empty());

        assertThrows(DeliveryNotFoundException.class,
                () -> testee.deliverPack(packId, userName));

        verify(delivery, times(0)).setDeliveredAt(any(LocalDateTime.class));
    }
}
