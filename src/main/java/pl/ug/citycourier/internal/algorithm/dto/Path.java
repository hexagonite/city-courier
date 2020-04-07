package pl.ug.citycourier.internal.algorithm.dto;

import java.util.Objects;

public class Path implements Comparable<Path>{
    private Double distance;

    public Path(Double distance) {
        this.distance = distance;
    }

    public Path() {
        this.distance = 0.0;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public void sumWithPath(Path path) {
        this.distance += path.distance;
    }

    @Override
    public int compareTo(Path path) {
        return this.distance.compareTo(path.distance);
    }

    public boolean equals(Path path) {
        return Objects.equals(this.distance, path.distance);
    }
}
