package pl.ug.citycourier.internal.delivery;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import pl.ug.citycourier.internal.common.EntityNotFoundException;
import pl.ug.citycourier.internal.location.LocationService;
import pl.ug.citycourier.internal.pack.Pack;
import pl.ug.citycourier.internal.pack.PackNotFoundException;
import pl.ug.citycourier.internal.pack.PackRepository;
import pl.ug.citycourier.internal.pack.PackService;
import pl.ug.citycourier.internal.security.boundary.RoleName;
import pl.ug.citycourier.internal.security.entity.Role;
import pl.ug.citycourier.internal.user.User;
import pl.ug.citycourier.internal.user.UserNotFoundException;
import pl.ug.citycourier.internal.user.UserRepository;
import pl.ug.citycourier.internal.user.UserService;

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
    private User user;
    @Mock
    private Pack pack;
    @Mock
    private Delivery delivery;

    @Test
    void addDelivery() throws UserNotFoundException {
        String userName = "name";
        when(userService.getCourierFromUsername(userName)).thenReturn(user);

        testee.addDelivery(newDeliveryDTO, userName);

        verify(deliveryRepository).save(any(Delivery.class));
    }

    @Test
    void addDelivery_userNotFound_shouldThrowException() throws UserNotFoundException {
        String userName = "name";
        when(userService.getCourierFromUsername(userName)).thenThrow(UserNotFoundException.class);

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
        verify(delivery).setGetPackDate(any(LocalDateTime.class));
    }


    @Test
    void getPackFromClient_courierNotFound() throws UserNotFoundException {
        long packId = 1L;
        String userName = "name";

        when(userService.getCourierFromUsername(userName)).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class,
                () -> testee.getPackFromClient(packId, userName));

        verify(delivery, times(0)).setCourier(user);
        verify(delivery, times(0)).setGetPackDate(any(LocalDateTime.class));
    }

    @Test
    void getPackFromClient_userIsNotACourier() throws UserNotFoundException {
        long packId = 1L;
        String userName = "name";

        when(userService.getCourierFromUsername(userName)).thenThrow(BadCredentialsException.class);

        assertThrows(BadCredentialsException.class,
                () -> testee.getPackFromClient(packId, userName));

        verify(delivery, times(0)).setCourier(user);
        verify(delivery, times(0)).setGetPackDate(any(LocalDateTime.class));
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
        verify(delivery, times(0)).setGetPackDate(any(LocalDateTime.class));
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
        verify(delivery, times(0)).setGetPackDate(any(LocalDateTime.class));
    }


    @Test
    void deliverPack() throws EntityNotFoundException {
        long packId = 1L;
        String userName = "name";

        when(userService.getCourierFromUsername(userName)).thenReturn(user);
        when(packRepository.findById(packId)).thenReturn(Optional.of(pack));
        when(deliveryRepository.findByPack(pack)).thenReturn(Optional.of(delivery));

        testee.deliverPack(packId, userName);

        verify(delivery).setDeliverPackDate(any(LocalDateTime.class));
    }


    @Test
    void deliverPack_courierNotFound() throws UserNotFoundException {
        long packId = 1L;
        String userName = "name";

        when(userService.getCourierFromUsername(userName)).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class,
                () -> testee.deliverPack(packId, userName));

        verify(delivery, times(0)).setDeliverPackDate(any(LocalDateTime.class));
    }

    @Test
    void deliverPack_userIsNotACourier() throws UserNotFoundException {
        long packId = 1L;
        String userName = "name";

        when(userService.getCourierFromUsername(userName)).thenThrow(BadCredentialsException.class);

        assertThrows(BadCredentialsException.class,
                () -> testee.deliverPack(packId, userName));

        verify(delivery, times(0)).setDeliverPackDate(any(LocalDateTime.class));
    }


    @Test
    void deliverPack_packNotFound() throws UserNotFoundException {
        long packId = 1L;
        String userName = "name";

        when(userService.getCourierFromUsername(userName)).thenReturn(user);
        when(packRepository.findById(packId)).thenReturn(Optional.empty());

        assertThrows(PackNotFoundException.class,
                () -> testee.deliverPack(packId, userName));

        verify(delivery, times(0)).setDeliverPackDate(any(LocalDateTime.class));
    }

    @Test
    void deliverPack_deliveryNotFound() throws UserNotFoundException {
        long packId = 1L;
        String userName = "name";

        when(userService.getCourierFromUsername(userName)).thenReturn(user);
        when(packRepository.findById(packId)).thenReturn(Optional.of(pack));
        when(deliveryRepository.findByPack(pack)).thenReturn(Optional.empty());

        assertThrows(DeliveryNotFoundException.class,
                () -> testee.deliverPack(packId, userName));

        verify(delivery, times(0)).setDeliverPackDate(any(LocalDateTime.class));
    }
}
