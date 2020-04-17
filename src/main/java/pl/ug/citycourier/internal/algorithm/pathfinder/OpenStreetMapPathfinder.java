package pl.ug.citycourier.internal.algorithm.pathfinder;

import org.springframework.stereotype.Component;
import pl.ug.citycourier.internal.algorithm.dto.Path;
import pl.ug.citycourier.internal.coordinate.CoordinatePairDTO;
import pl.ug.citycourier.internal.location.Location;

@Component
public class OpenStreetMapPathfinder implements Pathfinder {
    //TODO
    @Override
    public Path findShortestPath(Location start, Location finish) {
        return new Path(0.0);
    }

    @Override
    public Path findShortestPath(CoordinatePairDTO coordinatePairDTO, Location finish) {
        return new Path(0.0);
    }
}
