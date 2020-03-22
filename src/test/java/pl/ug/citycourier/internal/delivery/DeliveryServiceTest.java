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

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
    private UserRepository userRepository;
    @Mock
    private PackRepository packRepository;
    @Mock
    private PackService packService;
    @Mock
    private LocationService locationService;

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
        when(userRepository.findByUsername(userName)).thenReturn(Optional.of(user));

        testee.addDelivery(newDeliveryDTO, userName);

        verify(deliveryRepository).save(any(Delivery.class));
    }

    @Test
    void addDelivery_userNotFound_shouldThrowException() {
        String userName = "name";
        when(userRepository.findByUsername(userName)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> testee.addDelivery(newDeliveryDTO, userName));

        verifyNoInteractions(deliveryRepository);
    }

    @Test
    void getPackFromClient() throws EntityNotFoundException {
        long packId = 1L;
        String username = "name";

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(user.getRole()).thenReturn(new Role(1, RoleName.COURIER));
        when(packRepository.findById(packId)).thenReturn(Optional.of(pack));
        when(deliveryRepository.findByPack(pack)).thenReturn(Optional.of(delivery));

        testee.getPackFromClient(packId, username);

        verify(delivery).setCourier(user);
        verify(delivery).setGetPackDate(any(LocalDateTime.class));
    }


    @Test
    void getPackFromClient_courierNotFound() {
        long packId = 1L;
        String username = "name";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> testee.getPackFromClient(packId, username));

        verify(delivery, times(0)).setCourier(user);
        verify(delivery, times(0)).setGetPackDate(any(LocalDateTime.class));
    }

    @Test
    void getPackFromClient_userIsNotACourier() {
        long packId = 1L;
        String username = "name";

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(user.getRole()).thenReturn(new Role(1, RoleName.CLIENT));

        assertThrows(BadCredentialsException.class,
                () -> testee.getPackFromClient(packId, username));

        verify(delivery, times(0)).setCourier(user);
        verify(delivery, times(0)).setGetPackDate(any(LocalDateTime.class));
    }


    @Test
    void getPackFromClient_packNotFound() {
        long packId = 1L;
        String username = "name";

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(user.getRole()).thenReturn(new Role(1, RoleName.COURIER));
        when(packRepository.findById(packId)).thenReturn(Optional.empty());

        assertThrows(PackNotFoundException.class,
                () -> testee.getPackFromClient(packId, username));

        verify(delivery, times(0)).setCourier(user);
        verify(delivery, times(0)).setGetPackDate(any(LocalDateTime.class));
    }

    @Test
    void getPackFromClient_deliveryNotFound() {
        long packId = 1L;
        String username = "name";

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(user.getRole()).thenReturn(new Role(1, RoleName.COURIER));
        when(packRepository.findById(packId)).thenReturn(Optional.of(pack));
        when(deliveryRepository.findByPack(pack)).thenReturn(Optional.empty());

        assertThrows(DeliveryNotFoundException.class,
                () -> testee.getPackFromClient(packId, username));

        verify(delivery, times(0)).setCourier(user);
        verify(delivery, times(0)).setGetPackDate(any(LocalDateTime.class));
    }


    @Test
    void deliverPack() throws EntityNotFoundException {
        long packId = 1L;
        String username = "name";

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(user.getRole()).thenReturn(new Role(1, RoleName.COURIER));
        when(packRepository.findById(packId)).thenReturn(Optional.of(pack));
        when(deliveryRepository.findByPack(pack)).thenReturn(Optional.of(delivery));

        testee.deliverPack(packId, username);

        verify(delivery).setDeliverPackDate(any(LocalDateTime.class));
    }


    @Test
    void deliverPack_courierNotFound() {
        long packId = 1L;
        String username = "name";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> testee.deliverPack(packId, username));

        verify(delivery, times(0)).setDeliverPackDate(any(LocalDateTime.class));
    }

    @Test
    void deliverPack_userIsNotACourier() {
        long packId = 1L;
        String username = "name";

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(user.getRole()).thenReturn(new Role(1, RoleName.CLIENT));

        assertThrows(BadCredentialsException.class,
                () -> testee.deliverPack(packId, username));

        verify(delivery, times(0)).setDeliverPackDate(any(LocalDateTime.class));
    }


    @Test
    void deliverPack_packNotFound() {
        long packId = 1L;
        String username = "name";

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(user.getRole()).thenReturn(new Role(1, RoleName.COURIER));
        when(packRepository.findById(packId)).thenReturn(Optional.empty());

        assertThrows(PackNotFoundException.class,
                () -> testee.deliverPack(packId, username));

        verify(delivery, times(0)).setDeliverPackDate(any(LocalDateTime.class));
    }

    @Test
    void deliverPack_deliveryNotFound() {
        long packId = 1L;
        String username = "name";

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(user.getRole()).thenReturn(new Role(1, RoleName.COURIER));
        when(packRepository.findById(packId)).thenReturn(Optional.of(pack));
        when(deliveryRepository.findByPack(pack)).thenReturn(Optional.empty());

        assertThrows(DeliveryNotFoundException.class,
                () -> testee.deliverPack(packId, username));

        verify(delivery, times(0)).setDeliverPackDate(any(LocalDateTime.class));
    }
}
