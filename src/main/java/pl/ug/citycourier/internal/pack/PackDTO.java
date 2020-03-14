package pl.ug.citycourier.internal.pack;

public class PackDTO {

    private PackSize packSize;
    private String description;

    public PackDTO(PackSize packSize, String description) {
        this.packSize = packSize;
        this.description = description;
    }

    public PackSize getPackSize() {
        return packSize;
    }

    public String getDescription() {
        return description;
    }
}
