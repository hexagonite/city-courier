package pl.ug.citycourier.internal.algorithm.pathfinder;

import pl.ug.citycourier.internal.location.Location;

public interface Pathfinder {

    double findShortestDistanceBetweenTwoLocations(Location start, Location finish);

}
