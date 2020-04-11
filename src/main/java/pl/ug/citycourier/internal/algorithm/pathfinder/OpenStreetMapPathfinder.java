package pl.ug.citycourier.internal.algorithm.pathfinder;

import pl.ug.citycourier.internal.algorithm.dto.Path;
import pl.ug.citycourier.internal.location.Location;

public class OpenStreetMapPathfinder extends Pathfinder {
    //TODO
    @Override
    public Path findShortestPath(Location start, Location finish) {
        try {
            throw new Exception("Not implemented!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Path(0.0);
    }
}
