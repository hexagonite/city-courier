package pl.ug.citycourier.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.ug.citycourier.internal.security.boundary.LoginDto;
import pl.ug.citycourier.internal.security.boundary.UserSecurityService;
import pl.ug.citycourier.internal.security.internal.config.JwtTokenProvider;
import pl.ug.citycourier.internal.security.internal.exception.UserDtoValidationException;
import pl.ug.citycourier.internal.security.internal.exception.WrongTokenException;
import pl.ug.citycourier.internal.security.internal.service.AuthValidationErrorService;
import pl.ug.citycourier.internal.user.User;
import pl.ug.citycourier.internal.user.UserDto;
import pl.ug.citycourier.internal.user.UserWithRoleDto;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * trzeba dodać dwie role do tabeli Role: jedna z id=1, name=USER, i id=2, name=ADMIN
 * <p>
 * bez tokena wejdziecie tylko na dwie strony: /registration oraz /login
 * <p>
 * /registration przyjmuje dto z wartościami:
 * {
 * "name":"name3",
 * "surname": "surname3",
 * "username": "username3",
 * "password": "123",
 * "matchingPassword": "123",
 * "email": "asd3@gmail.com"
 * }
 * <p>
 * jeśli uda się zarejestrować, zwraca token w formacie: Bearer WŁAŚCIWY_TOKEN
 * <p>
 * /login przyjmuje takiego dtoka:
 * {
 * "username":"username3",
 * "password":"123"
 * }
 * zwraca to samo, co register
 * <p>
 * i teraz trzeba wsadzać ten token do headerów przy każdym zapytaniu API w formacie:
 * Authorization: Bearer WŁAŚCIWY_TOKEN
 * <p>
 * są zrobione dwa specjalne endpointy do testowania tego:
 * /user - zwraca Hello user, jeśli osoba jest zalogowana(w headerze był token)
 * /admin - zwraca Hello admin, jeśli osoba jest zalogowana I jej rola to ADMIN(roleId = 2)
 */

@RestController
public class AuthController {
    private UserSecurityService userSecurityService;
    private JwtTokenProvider tokenProvider;
    private AuthValidationErrorService authValidationErrorService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserSecurityService userSecurityService, JwtTokenProvider tokenProvider, AuthValidationErrorService authValidationErrorService, AuthenticationManager authenticationManager) {
        this.userSecurityService = userSecurityService;
        this.tokenProvider = tokenProvider;
        this.authValidationErrorService = authValidationErrorService;
        this.authenticationManager = authenticationManager;
    }

    @CrossOrigin
    @PostMapping("/login")
    public String authenticateUser(@Valid @RequestBody LoginDto loginDto, BindingResult result) {
        authValidationErrorService.validateFromBindingResult(result);
        Authentication authentication = createAuthentication(loginDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return createJwtToken(authentication);
    }

    private Authentication createAuthentication(@RequestBody @Valid LoginDto loginDto) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );
    }

    private String createJwtToken(Authentication authentication) {
        return "Bearer " + tokenProvider.generateToken(authentication);
    }

    @CrossOrigin
    @PostMapping("/registration")
    public String registerUserAccount(@RequestBody UserDto accountDto) throws UserDtoValidationException {
        System.out.println("Registering user account with information: " + accountDto);
        userSecurityService.registerNewUserAccount(accountDto);
        Authentication authentication = createAuthentication(new LoginDto(accountDto.getUsername(), accountDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return createJwtToken(authentication);
    }

    @GetMapping("/registrationConfirm")
    public void confirmRegistration(@RequestParam("token") final String token) throws WrongTokenException {
        userSecurityService.validateVerificationToken(token);
        final User user = userSecurityService.getUser(token);
        authWithoutPassword(user);
    }

    @PostMapping("/admin/createUserAccount")
    public void createUserAccount(@RequestBody UserWithRoleDto accountDto) throws UserDtoValidationException {
        userSecurityService.registerNewUserAccount(accountDto);
        Authentication authentication = createAuthentication(new LoginDto(accountDto.getUsername(), accountDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void authWithoutPassword(User user) {
        List<String> role = new ArrayList<>();
        role.add(user.getRole().toString());
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, getAuthorities(role));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private List<GrantedAuthority> getAuthorities(List<String> roles) {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    //TEST ENDPOINT
    @GetMapping("/client")
    public String helloClient() {
        return "Hello client";
    }

    //TEST ENDPOINT
    @GetMapping("/admin")
    public String helloAdmin() {
        return "Hello admin";
    }

    //TEST ENDPOINT
    @GetMapping("/courier")
    public String helloCourier() {
        return "Hello courier";
    }

}
