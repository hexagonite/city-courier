package pl.ug.citycourier.internal.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import pl.ug.citycourier.internal.security.boundary.RoleName;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCourierFromUsername(String userName) throws UserNotFoundException {
        User courier = userRepository.findByUsername(userName).orElseThrow(UserNotFoundException::new);
        if (courier.getRole().getName() == RoleName.COURIER) {
            return courier;
        }
        throw new BadCredentialsException("You need to be courier to get pack from client");
    }

}
