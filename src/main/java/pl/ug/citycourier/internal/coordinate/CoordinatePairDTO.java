package pl.ug.citycourier.internal.coordinate;

public class CoordinatePairDTO {

    private double longitute;
    private double latitude;

    public CoordinatePairDTO() {
    }

    public CoordinatePairDTO(double longitute, double latitude) {
        this.longitute = longitute;
        this.latitude = latitude;
    }

    public CoordinatePair createCoordinatePair() {
        CoordinatePair coordinatePair = new CoordinatePair();
        coordinatePair.setLongitude(longitute);
        coordinatePair.setLatitude(latitude);
        return coordinatePair;
    }

    public double getLongitute() {
        return longitute;
    }

    public void setLongitute(double longitute) {
        this.longitute = longitute;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
