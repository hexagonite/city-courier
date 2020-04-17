package pl.ug.citycourier.internal.coordinate;

public class CoordinatePairDTO {

    private double longitude;
    private double latitude;

    public CoordinatePairDTO() {
    }

    public CoordinatePairDTO(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public CoordinatePair createCoordinatePair() {
        CoordinatePair coordinatePair = new CoordinatePair();
        coordinatePair.setLongitude(longitude);
        coordinatePair.setLatitude(latitude);
        return coordinatePair;
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
