package pl.ug.citycourier.internal.delivery;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import pl.ug.citycourier.internal.location.LocationService;
import pl.ug.citycourier.internal.pack.PackService;
import pl.ug.citycourier.internal.user.User;
import pl.ug.citycourier.internal.user.UserNotFoundException;
import pl.ug.citycourier.internal.user.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DeliveryServiceTest {

    @InjectMocks
    private DeliveryService testee;

    @Mock
    private DeliveryRepository deliveryRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PackService packService;
    @Mock

    private LocationService locationService;

    @Mock
    private NewDeliveryDTO newDeliveryDTO;
    @Mock
    private User user;

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

}
