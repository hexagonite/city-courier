package pl.ug.citycourier.internal.pack;

public enum PackSize {
    SMALL(1.00F),
    MEDIUM(1.25F),
    BIG(1.5F);
    private float converter;

    PackSize(float converter) {
        this.converter = converter;
    }

    public float getConverter() {
        return converter;
    }
}
