package pl.ug.citycourier.internal.pathfinder.osrm;

import pl.ug.citycourier.internal.coordinate.CoordinatePairDTO;

public class OsrmUrlCreator {

    private final static String START_URL = "http://router.project-osrm.org/route/v1";
    private final static String DRIVING = "/driving";
    private final static String COMMA = ",";
    private final static String SEMICOLON = ";";

    public static String createOsrmUrlForDrivingCoordinates(CoordinatePairDTO start, CoordinatePairDTO finish) {
        StringBuilder sb = new StringBuilder();
        sb.append(START_URL);
        sb.append(DRIVING);
        sb.append("/");
        sb.append(createStringFromCoordinatePairDto(start));
        sb.append(SEMICOLON);
        sb.append(createStringFromCoordinatePairDto(finish));
        return sb.toString();
    }

    private static String createStringFromCoordinatePairDto(CoordinatePairDTO pair) {
        return pair.getLongitude() + COMMA + pair.getLatitude();
    }
}
