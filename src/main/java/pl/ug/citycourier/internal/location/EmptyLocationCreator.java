package pl.ug.citycourier.internal.location;

import org.springframework.stereotype.Component;
import pl.ug.citycourier.internal.coordinate.CoordinatePairDTO;

@Component
public class EmptyLocationCreator implements LocationCreator {

    @Override
    public Location createLocationFromCoordinatePair(CoordinatePairDTO coordinatePairDTO) {
        return LocationBuilder.aLocation()
                .withCity("City")
                .withHouseNumber("HouseNumber")
                .withStreet("Street")
                .withZipCode("ZipCode")
                .build();
    }
}
