package pl.ug.citycourier.internal.algorithm.dto;

import pl.ug.citycourier.internal.coordinate.CoordinatePairDTO;
import pl.ug.citycourier.internal.location.Location;
import pl.ug.citycourier.internal.user.User;

public class CourierWithCoordinates {
    private User courier;
    private CoordinatePairDTO coordinatePairDTO;

    public CourierWithCoordinates(User courier, CoordinatePairDTO coordinatePairDTO) {
        this.courier = courier;
        this.coordinatePairDTO = coordinatePairDTO;
    }

    public User getCourier() {
        return courier;
    }

    public void setCourier(User courier) {
        this.courier = courier;
    }

    public CoordinatePairDTO getCoordinatePairDTO() {
        return coordinatePairDTO;
    }

    public void setCoordinatePairDTO(CoordinatePairDTO coordinatePairDTO) {
        this.coordinatePairDTO = coordinatePairDTO;
    }
}
