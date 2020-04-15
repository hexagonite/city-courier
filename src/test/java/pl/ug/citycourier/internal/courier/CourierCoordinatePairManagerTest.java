package pl.ug.citycourier.internal.courier;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import pl.ug.citycourier.internal.coordinate.CoordinatePairDTO;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CourierCoordinatePairManagerTest {

    @InjectMocks
    private CourierCoordinatePairManager testee;

    @Mock
    private CoordinatePairDTO coordinatePairDTO;

    @Test
    public void getCoordinatePairForCourierName() throws CoordinatePairForCourierNameNotFound {
        String courierName = "courier";
        testee.actualiseCoordinatePairForCourier(courierName, coordinatePairDTO);

        CoordinatePairDTO result = testee.getCoordinatePairForCourierName(courierName);

        assertThat(result).isEqualTo(coordinatePairDTO);
    }

    @Test
    public void getCoordinatePairForCourierName_notAdded() {
        String courierName = "courier";

        assertThrows(CoordinatePairForCourierNameNotFound.class, () -> testee.getCoordinatePairForCourierName(courierName));
    }

    @Test
    public void actualiseCoordinatePairForCourier() throws CoordinatePairForCourierNameNotFound {
        String courierName = "courier";

        testee.actualiseCoordinatePairForCourier(courierName, new CoordinatePairDTO());
        testee.actualiseCoordinatePairForCourier(courierName, coordinatePairDTO);

        CoordinatePairDTO result = testee.getCoordinatePairForCourierName(courierName);
        assertThat(result).isEqualTo(coordinatePairDTO);
    }
}
