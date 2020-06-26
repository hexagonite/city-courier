package pl.ug.citycourier.internal.algorithm.runner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.ug.citycourier.internal.coordinate.CoordinatePairDTO;
import pl.ug.citycourier.internal.coordinate.CoordinatePairRepository;
import pl.ug.citycourier.internal.courier.CourierCoordinatePairManager;
import pl.ug.citycourier.internal.delivery.*;
import pl.ug.citycourier.internal.location.LocationRepository;
import pl.ug.citycourier.internal.pack.PackRepository;
import pl.ug.citycourier.internal.pack.PackSize;
import pl.ug.citycourier.internal.security.boundary.UserSecurityService;
import pl.ug.citycourier.internal.security.internal.exception.UserDtoValidationException;
import pl.ug.citycourier.internal.user.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.ug.citycourier.internal.user.Status.AVAILABLE;

@SpringBootTest
public class AlgorithmRunnerTest {

    private AlgorithmRunner algorithmRunner;
    private CourierCoordinatePairManager courierCoordinatePairManager;
    private DeliveryService deliveryService;
    private UserSecurityService userSecurityService;

    private CoordinatePairRepository coordinatePairRepository;
    private DeliveryRepository deliveryRepository;
    private LocationRepository locationRepository;
    private PackRepository packRepository;
    private UserRepository userRepository;
    private UserService userService;

    @Autowired
    public AlgorithmRunnerTest(AlgorithmRunner algorithmRunner, CourierCoordinatePairManager courierCoordinatePairManager,
                                DeliveryService deliveryService, UserService userService,
                               UserSecurityService userSecurityService, CoordinatePairRepository coordinatePairRepository,
                               DeliveryRepository deliveryRepository, LocationRepository locationRepository,
                               PackRepository packRepository, UserRepository userRepository
                               ) {
        this.algorithmRunner = algorithmRunner;
        this.courierCoordinatePairManager = courierCoordinatePairManager;
        this.deliveryService = deliveryService;
        this.userSecurityService = userSecurityService;
        this.coordinatePairRepository = coordinatePairRepository;
        this.deliveryRepository = deliveryRepository;
        this.locationRepository = locationRepository;
        this.packRepository = packRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    private User courier1;
    private User courier2;
    private User client;
    private Delivery deliveryA;
    private Delivery deliveryB;
    private Delivery deliveryC;
    private Delivery deliveryD;
    private Delivery deliveryE;

    @BeforeEach
    void setUp() throws UserDtoValidationException, UserNotFoundException {
        //arrange
        UserDto courierUserDto1 = new UserDto("courier1", "courier1", "courier1", "courier1", "courier1", "courier1@courier1.pl");
        UserDto courierUserDto2 = new UserDto("courier2", "courier2", "courier2", "courier2", "courier2", "courier2@courier2.pl");
        UserDto clientUserDto = new UserDto("client1", "client1", "client1", "client1", "client1", "client1@client1.pl");

        courier1 = userSecurityService.registerNewUserAccount(courierUserDto1);
        courier2 = userSecurityService.registerNewUserAccount(courierUserDto2);
        client = userSecurityService.registerNewUserAccount(clientUserDto);

        NewDeliveryDTO newDeliveryDtoA = new NewDeliveryDTO(PackSize.SMALL, "Delivery1", new CoordinatePairDTO(54.346608, 18.630401), new CoordinatePairDTO(54.365732, 18.630261));
        NewDeliveryDTO newDeliveryDtoB = new NewDeliveryDTO(PackSize.SMALL, "Delivery2", new CoordinatePairDTO(54.344483, 18.646669), new CoordinatePairDTO(54.350205, 18.655364));
        NewDeliveryDTO newDeliveryDtoC = new NewDeliveryDTO(PackSize.SMALL, "Delivery3", new CoordinatePairDTO(54.340722, 18.607261), new CoordinatePairDTO(54.335980, 18.630541));
        NewDeliveryDTO newDeliveryDtoD = new NewDeliveryDTO(PackSize.SMALL, "Delivery4", new CoordinatePairDTO(54.336553, 18.611468), new CoordinatePairDTO(54.334590, 18.590152));
        NewDeliveryDTO newDeliveryDtoE = new NewDeliveryDTO(PackSize.SMALL, "Delivery5", new CoordinatePairDTO(54.343992, 18.598286), new CoordinatePairDTO(54.365241, 18.580475));

        deliveryA = deliveryService.addDelivery(newDeliveryDtoA, client.getUsername());
        deliveryB = deliveryService.addDelivery(newDeliveryDtoB, client.getUsername());
        deliveryC = deliveryService.addDelivery(newDeliveryDtoC, client.getUsername());
        deliveryD = deliveryService.addDelivery(newDeliveryDtoD, client.getUsername());
        deliveryE = deliveryService.addDelivery(newDeliveryDtoE, client.getUsername());

        courierCoordinatePairManager.updateCourierCoordinatePair("courier1", new CoordinatePairDTO(54.343420, 18.633626));
        courierCoordinatePairManager.updateCourierCoordinatePair("courier2", new CoordinatePairDTO(54.340559, 18.594920));

        userService.updateStatus(courier1.getUsername(), AVAILABLE);
        userService.updateStatus(courier2.getUsername(), AVAILABLE);
    }

    @Test
    public void algorithmRunnerTest() throws DeliveryNotFoundException {
        //act
        algorithmRunner.run();
        Long deliveryAAssignedCourierId = deliveryService.findById(deliveryA.getId()).getCourier().getId();
        Long deliveryBAssignedCourierId = deliveryService.findById(deliveryB.getId()).getCourier().getId();
        Long deliveryDAssignedCourierId = deliveryService.findById(deliveryD.getId()).getCourier().getId();
        Long deliveryEAssignedCourierId = deliveryService.findById(deliveryE.getId()).getCourier().getId();

        //assert
        assertAll(
                () -> assertEquals(deliveryAAssignedCourierId, courier1.getId()),
                () -> assertEquals(deliveryBAssignedCourierId, courier1.getId()),
                () -> assertEquals(deliveryDAssignedCourierId, courier2.getId()),
                () -> assertEquals(deliveryEAssignedCourierId, courier2.getId())
        );
    }

    @AfterEach
    void tearDown() {
        deliveryRepository.deleteAll();
        locationRepository.deleteAll();
        coordinatePairRepository.deleteAll();
        packRepository.deleteAll();
        userRepository.deleteAll();
    }

}


