package pl.ug.citycourier.internal.algorithm.pathfinder;

import org.springframework.stereotype.Component;
import pl.ug.citycourier.internal.algorithm.dto.Path;
import pl.ug.citycourier.internal.location.Location;

@Component
public class LinearPathfinder extends Pathfinder {

    //Harvesine formula - optimised: https://stackoverflow.com/a/21623206/
    @Override
    public Path findShortestPath(Location start, Location finish) {
        double lat1 = start.getCoordinatePair().getLatitude();
        double lat2 = finish.getCoordinatePair().getLatitude();
        double lon1 = start.getCoordinatePair().getLongitude();
        double lon2 = finish.getCoordinatePair().getLongitude();

        double p = Math.PI / 180;
        double R = 6371;
        double a = 0.5 - Math.cos((lat2 - lat1) * p) / 2 + Math.cos(lat1 * p) * Math.cos(lat2 * p) *
                        (1 - Math.cos((lon2 - lon1) * p)) / 2;

        return new Path(2 * R * Math.asin(Math.sqrt(a)));
    }

}
