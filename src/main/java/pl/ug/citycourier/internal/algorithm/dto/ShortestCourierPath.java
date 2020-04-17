package pl.ug.citycourier.internal.algorithm.dto;

import java.util.List;

public class ShortestCourierPath implements Comparable<ShortestCourierPath>{
    private PathToDelivery entirePath;
    private List<LocationWithType> locations;
    private int index;

    public ShortestCourierPath(PathToDelivery entirePath, List<LocationWithType> locations, int index) {
        this.entirePath = entirePath;
        this.locations = locations;
        this.index = index;
    }

    public PathToDelivery getEntirePath() {
        return entirePath;
    }

    public void setEntirePath(PathToDelivery entirePath) {
        this.entirePath = entirePath;
    }

    public List<LocationWithType> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationWithType> locations) {
        this.locations = locations;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int compareTo(ShortestCourierPath o) {
        return entirePath.compareTo(o.entirePath);
    }

}
