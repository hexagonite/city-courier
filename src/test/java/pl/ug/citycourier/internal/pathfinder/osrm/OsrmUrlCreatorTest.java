package pl.ug.citycourier.internal.pathfinder.osrm;

import org.junit.jupiter.api.Test;
import pl.ug.citycourier.internal.coordinate.CoordinatePairDTO;

import static org.assertj.core.api.Assertions.assertThat;


class OsrmUrlCreatorTest {

    @Test
    void createOsrmUrlForDrivingCoordinates() {
        CoordinatePairDTO start = new CoordinatePairDTO(25.0, 50.0);
        CoordinatePairDTO finish = new CoordinatePairDTO(20.0, 40.0);

        String result = OsrmUrlCreator.createOsrmUrlForDrivingCoordinates(start, finish);

        assertThat(result).isEqualTo("http://router.project-osrm.org/route/v1/driving/25.0,50.0;20.0,40.0");
    }
}