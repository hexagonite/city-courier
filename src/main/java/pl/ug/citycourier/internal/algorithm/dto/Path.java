package pl.ug.citycourier.internal.algorithm.dto;

import pl.ug.citycourier.internal.pathfinder.osrn.Route;

import java.util.Objects;

public class Path implements Comparable<Path> {

    private Double distance;
    private Double time;

    public Path(Route route) {
        this.distance = (double) route.getDistanceInMeters();
        this.time = (double) route.getDurationInSeconds();
    }

    public Path(Double distance) {
        this.distance = distance;
    }

    public Path(Path... paths) {
        this.distance = 0.0;
        for (Path path : paths) {
            this.distance += path.distance;
        }
    }

    public Double getDistance() {
        return distance;
    }

    public Double getTime() {
        return time;
    }

    @Override
    public int compareTo(Path path) {
        return this.distance.compareTo(path.distance);
    }

    public boolean equals(Path path) {
        return Objects.equals(this.distance, path.distance);
    }
}
