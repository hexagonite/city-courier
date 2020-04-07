package pl.ug.citycourier.internal.algorithm.pathfinder;

import pl.ug.citycourier.internal.algorithm.dto.Path;
import pl.ug.citycourier.internal.location.Location;

public abstract class Pathfinder {

    public abstract Path findShortestPath(Location start, Location finish);

    public Path sumPaths(Path... paths) {
        Path sum = new Path();
        for (Path path : paths) {
            sum.sumWithPath(path);
        }
        return sum;
    }

}
