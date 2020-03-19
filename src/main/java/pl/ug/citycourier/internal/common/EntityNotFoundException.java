package pl.ug.citycourier.internal.common;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException() { }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
