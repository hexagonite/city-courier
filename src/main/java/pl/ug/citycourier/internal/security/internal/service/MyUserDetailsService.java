package pl.ug.citycourier.internal.security.internal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ug.citycourier.internal.user.User;
import pl.ug.citycourier.internal.user.UserRepository;

import java.util.Collections;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + username));
        Set<GrantedAuthority> grantedAuthorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString()));
        System.out.println(grantedAuthorities);
        return createSpringSecurityUser(user, grantedAuthorities);
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(User user, Set<GrantedAuthority> grantedAuthorities) {
        return new org.springframework.security.core.userdetails.User
                (user.getUsername(), user.getPassword(), grantedAuthorities);
    }

    @Transactional
    public User loadUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) throw new UsernameNotFoundException("User not found");
        return user;

    }
}