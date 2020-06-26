package pl.ug.citycourier.internal.security.boundary;

import pl.ug.citycourier.internal.security.entity.VerificationToken;
import pl.ug.citycourier.internal.security.internal.exception.UserDtoValidationException;
import pl.ug.citycourier.internal.security.internal.exception.WrongTokenException;
import pl.ug.citycourier.internal.user.User;
import pl.ug.citycourier.internal.user.UserDto;

public interface UserSecurityService {
    User registerNewUserAccount(UserDto accountDto) throws UserDtoValidationException, UserDtoValidationException;

    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    void validateVerificationToken(String token) throws WrongTokenException;

    VerificationToken generateNewVerificationToken(final String existingVerificationToken);
}
