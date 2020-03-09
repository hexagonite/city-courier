package pl.ug.citycourier.internal.delivery;

import pl.ug.citycourier.internal.coordinate.CoordinatePairDTO;
import pl.ug.citycourier.internal.pack.PackDTO;
import pl.ug.citycourier.internal.pack.PackSize;

public class NewDeliveryDTO {

    private PackSize packSize;
    private String description;
    private CoordinatePairDTO startCoordinates;
    private CoordinatePairDTO endCoordinates;

    public PackDTO createPackDTO() {
        return new PackDTO(packSize, description);
    }

    public PackSize getPackSize() {
        return packSize;
    }

    public void setPackSize(PackSize packSize) {
        this.packSize = packSize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CoordinatePairDTO getStartCoordinates() {
        return startCoordinates;
    }

    public void setStartCoordinates(CoordinatePairDTO startCoordinates) {
        this.startCoordinates = startCoordinates;
    }

    public CoordinatePairDTO getEndCoordinates() {
        return endCoordinates;
    }

    public void setEndCoordinates(CoordinatePairDTO endCoordinates) {
        this.endCoordinates = endCoordinates;
    }
}
