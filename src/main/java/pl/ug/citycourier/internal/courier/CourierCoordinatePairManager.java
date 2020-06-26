package pl.ug.citycourier.internal.courier;

import org.springframework.stereotype.Service;
import pl.ug.citycourier.internal.coordinate.CoordinatePairDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CourierCoordinatePairManager {

    private Map<String, CoordinatePairDTO> coordinatePairForCourierName = new HashMap<>();

    public CoordinatePairDTO getCoordinatePairForCourierName(String courierName) throws CoordinatePairForCourierNameNotFound {
        return Optional.ofNullable(coordinatePairForCourierName.get(courierName)).orElseThrow(CoordinatePairForCourierNameNotFound::new);
    }

    public void updateCourierCoordinatePair(String courierName, CoordinatePairDTO coordinatePairDTO) {
        coordinatePairForCourierName.put(courierName, coordinatePairDTO);
    }
}
