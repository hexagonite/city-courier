package pl.ug.citycourier.rest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.ug.citycourier.internal.user.User;

@Component
public class SecurityUserController {

    public String getUsernameFromContext() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else if (principal instanceof User) {
            return ((User) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
