package pl.ug.citycourier.internal.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ug.citycourier.internal.coordinate.CoordinatePair;
import pl.ug.citycourier.internal.coordinate.CoordinatePairDTO;
import pl.ug.citycourier.internal.coordinate.CoordinatePairRepository;

import java.util.Optional;

@Service
public class LocationService {

    private LocationRepository locationRepository;
    private CoordinatePairRepository coordinatePairRepository;
    private LocationCreator locationCreator;

    @Autowired
    public LocationService(LocationRepository locationRepository, CoordinatePairRepository coordinatePairRepository, LocationCreator locationCreator) {
        this.locationRepository = locationRepository;
        this.coordinatePairRepository = coordinatePairRepository;
        this.locationCreator = locationCreator;
    }

    public Location findOrCreateLocationByCoordinate(CoordinatePairDTO coordinatePairDTO) {
        Optional<CoordinatePair> coordinates = coordinatePairRepository
                .findByLatitudeEqualsAndLongitudeEquals(coordinatePairDTO.getLatitude(), coordinatePairDTO.getLongitute());
        if (coordinates.isPresent()) {
            return coordinates.get().getLocation();
        }
        return createNewLocationAndCoordinates(coordinatePairDTO);
    }

    private Location createNewLocationAndCoordinates(CoordinatePairDTO coordinatePairDTO) {
        Location location = locationCreator.createLocationFromCoordinatePair(coordinatePairDTO);
        CoordinatePair newCoordinates = coordinatePairRepository.save(coordinatePairDTO.createCoordinatePair());
        location.setCoordinatePair(newCoordinates);
        return locationRepository.save(location);
    }

}
