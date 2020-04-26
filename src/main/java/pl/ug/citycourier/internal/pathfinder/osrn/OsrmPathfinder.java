package pl.ug.citycourier.internal.pathfinder.osrn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.ug.citycourier.internal.algorithm.dto.Path;
import pl.ug.citycourier.internal.coordinate.CoordinatePairDTO;
import pl.ug.citycourier.internal.location.Location;
import pl.ug.citycourier.internal.pathfinder.Pathfinder;

@Component("openStreetMapPathfinder")
public class OsrmPathfinder implements Pathfinder {

    private RestTemplate restTemplate;

    @Autowired
    public OsrmPathfinder(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Path findShortestPath(Location start, Location finish) {
        CoordinatePairDTO startCoordinates = CoordinatePairDTO.createFromCoordinatePair(start.getCoordinatePair());
        CoordinatePairDTO finishCoordinates = CoordinatePairDTO.createFromCoordinatePair(finish.getCoordinatePair());
        return findShortestPathFromCoordinates(startCoordinates, finishCoordinates);
    }

    @Override
    public Path findShortestPath(CoordinatePairDTO startCoordinates, Location finish) {
        CoordinatePairDTO finishCoordinates = CoordinatePairDTO.createFromCoordinatePair(finish.getCoordinatePair());
        return findShortestPathFromCoordinates(startCoordinates, finishCoordinates);
    }

    private Path findShortestPathFromCoordinates(CoordinatePairDTO start, CoordinatePairDTO finish) {
        String url = OsrmUrlCreator.createOsrmUrlForDrivingCoordinates(start, finish);
        OsrmResponse response = restTemplate.getForObject(url, OsrmResponse.class);
        OsrmResponseValidator.validateResponse(response);
        Route fastestRoute = getFastestRoute(response);
        return new Path(fastestRoute);
    }

    private Route getFastestRoute(OsrmResponse response) {
        return response.getRoutes().get(0);
    }

}
