package pl.ug.citycourier.internal.courier;

import pl.ug.citycourier.internal.algorithm.dto.LocationWithType;
import pl.ug.citycourier.internal.delivery.Delivery;
import pl.ug.citycourier.internal.location.Location;
import pl.ug.citycourier.internal.location.LocationDTO;

public class CourierTask {
    private LocationDTO location;
    private CourierTaskType courierTaskType;
    private String recipientName;
    private String recipientSurname;
    private String packDescription;

    public CourierTask(LocationWithType locationWithType, Delivery delivery) {
        this.location = new LocationDTO(locationWithType.getLocation());
        this.courierTaskType = locationWithType.getCourierTaskType();
        this.recipientName = delivery.getClient().getName();
        this.recipientSurname = delivery.getClient().getSurname();
        this.packDescription = delivery.getPack().getDescription();
    }

    public CourierTask() {
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public CourierTaskType getCourierTaskType() {
        return courierTaskType;
    }

    public void setCourierTaskType(CourierTaskType courierTaskType) {
        this.courierTaskType = courierTaskType;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientSurname() {
        return recipientSurname;
    }

    public void setRecipientSurname(String recipientSurname) {
        this.recipientSurname = recipientSurname;
    }

    public String getPackDescription() {
        return packDescription;
    }

    public void setPackDescription(String packDescription) {
        this.packDescription = packDescription;
    }
}
