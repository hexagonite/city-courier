package pl.ug.citycourier.internal.coordinate;

public class CoordinatePairDTO {

    private double latitude;
    private double longitude;

    public CoordinatePairDTO() {
    }

    public CoordinatePairDTO(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public CoordinatePair createCoordinatePair() {
        CoordinatePair coordinatePair = new CoordinatePair();
        coordinatePair.setLatitude(latitude);
        coordinatePair.setLongitude(longitude);
        return coordinatePair;
    }

    public static CoordinatePairDTO createFromCoordinatePair(CoordinatePair coordinatePair) {
        return new CoordinatePairDTO(coordinatePair.getLatitude(), coordinatePair.getLongitude());
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
