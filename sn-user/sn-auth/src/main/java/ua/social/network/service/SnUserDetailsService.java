package ua.social.network.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ua.social.network.entity.User;
import ua.social.network.repository.UserRepository;

/**
 * @author Mykola Yashchenko
 */
@Service
public class SnUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public SnUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(this::toUser)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " doesn't exist"));
    }

    private org.springframework.security.core.userdetails.User toUser(User user) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                getGrantedAuthority(user));
    }

    private List<GrantedAuthority> getGrantedAuthority(User user) {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));
    }
}
