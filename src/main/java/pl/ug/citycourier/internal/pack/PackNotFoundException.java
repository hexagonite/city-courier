package pl.ug.citycourier.internal.pack;

import pl.ug.citycourier.internal.common.EntityNotFoundException;

public class PackNotFoundException extends EntityNotFoundException {
    public PackNotFoundException() {
        super("Pack entity not found!");
    }
}
