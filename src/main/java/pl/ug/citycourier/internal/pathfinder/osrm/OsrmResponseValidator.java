package pl.ug.citycourier.internal.pathfinder.osrm;

public class OsrmResponseValidator {

    private static String VALID_CODE = "OK";

    public static void validateResponse(OsrmResponse response) {
        if (response == null) {
            throw new OsrmPathFinderException("Response is null");
        } else if (codeResponseIsNotValid(response)) {
            throw new OsrmPathFinderException("Wrong response code, actual code:" + response.getCode() +
                    ", expected: " + VALID_CODE);
        } else if (response.getRoutes() == null || response.getRoutes().isEmpty()) {
            throw new OsrmPathFinderException("Expecting routes for response be not null or empty");
        }
    }

    private static boolean codeResponseIsNotValid(OsrmResponse response) {
        return !response.getCode().equals(VALID_CODE);
    }
}
