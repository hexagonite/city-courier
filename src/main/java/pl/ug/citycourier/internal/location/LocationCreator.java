package pl.ug.citycourier.internal.location;

import pl.ug.citycourier.internal.coordinate.CoordinatePairDTO;

public interface LocationCreator {
    Location createLocationFromCoordinatePair(CoordinatePairDTO coordinatePairDTO);
}
