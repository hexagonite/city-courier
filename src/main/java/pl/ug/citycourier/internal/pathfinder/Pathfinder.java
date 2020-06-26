package pl.ug.citycourier.internal.pathfinder;

import pl.ug.citycourier.internal.algorithm.dto.Path;
import pl.ug.citycourier.internal.coordinate.CoordinatePairDTO;
import pl.ug.citycourier.internal.location.Location;

public interface Pathfinder {

    Path findShortestPath(Location start, Location finish);

    Path findShortestPath(CoordinatePairDTO startCoordinates, Location finish);

}
