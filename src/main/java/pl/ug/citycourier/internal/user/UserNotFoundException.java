package pl.ug.citycourier.internal.user;

import pl.ug.citycourier.internal.common.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException() {
        super("User entity not found!");
    }
}
