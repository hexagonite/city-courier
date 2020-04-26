package pl.ug.citycourier.internal.pathfinder.osrn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Route {

    @JsonProperty(value = "duration")
    private float durationInSeconds;
    @JsonProperty(value = "distance")
    private float distanceInMeters;

    public float getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(float durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public float getDistanceInMeters() {
        return distanceInMeters;
    }

    public void setDistanceInMeters(float distanceInMeters) {
        this.distanceInMeters = distanceInMeters;
    }
}
