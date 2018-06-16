package ua.social.network.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ua.social.network.authservice.repository.UserRepository;
import ua.social.network.authservice.security.SnUserDetails;

/**
 * @author Mykola Yashchenko
 */
@Service
public class SnUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public SnUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(SnUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " doesn't exist"));
    }

}
