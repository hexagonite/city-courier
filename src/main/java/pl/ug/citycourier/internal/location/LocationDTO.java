package pl.ug.citycourier.internal.location;

import pl.ug.citycourier.internal.coordinate.CoordinatePairDTO;

public class LocationDTO {

    private CoordinatePairDTO coordinatePairDTO;
    private String city;
    private String street;
    private String houseNumber;
    private String zipCode;

    public LocationDTO() {
    }

    public LocationDTO(CoordinatePairDTO coordinatePairDTO, String city, String street, String houseNumber, String zipCode) {
        this.coordinatePairDTO = coordinatePairDTO;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
    }

    public LocationDTO(Location location) {
        this.coordinatePairDTO = CoordinatePairDTO.createFromCoordinatePair(location.getCoordinatePair());
        this.city = location.getCity();
        this.street = location.getStreet();
        this.houseNumber = location.getHouseNumber();
        this.zipCode = location.getZipCode();
    }

    public CoordinatePairDTO getCoordinatePairDTO() {
        return coordinatePairDTO;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getZipCode() {
        return zipCode;
    }
}
