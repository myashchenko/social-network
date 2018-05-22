package ua.social.network.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import ua.social.network.dto.CreateUserRequest;
import ua.social.network.entity.Role;
import ua.social.network.entity.User;
import ua.social.network.repository.FileRepository;
import ua.social.network.repository.UserRepository;
import ua.social.network.service.UserService;

/**
 * @author Mykola Yashchenko
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public String create(final CreateUserRequest request) {
        final User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        return userRepository.save(user).getId();
    }
}
