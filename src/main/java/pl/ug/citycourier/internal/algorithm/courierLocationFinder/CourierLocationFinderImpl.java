package pl.ug.citycourier.internal.algorithm.courierLocationFinder;

import org.springframework.stereotype.Component;
import pl.ug.citycourier.internal.location.Location;

import java.util.ArrayList;

@Component
public class CourierLocationFinderImpl implements CourierLocationFinder {
    @Override
    public ArrayList<Location> findAllCouriersLocation() {
        return new ArrayList<>();
    }
}
