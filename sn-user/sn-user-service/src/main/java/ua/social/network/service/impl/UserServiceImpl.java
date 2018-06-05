package ua.social.network.service.impl;

import java.util.Objects;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import ua.social.network.dto.CreateUserRequest;
import ua.social.network.dto.ModifyUserRequest;
import ua.social.network.entity.Role;
import ua.social.network.entity.User;
import ua.social.network.exception.SnException;
import ua.social.network.exception.UserServiceExceptionDetails;
import ua.social.network.oauth2.principal.SnPrincipal;
import ua.social.network.repository.UserRepository;
import ua.social.network.service.UserService;

/**
 * @author Mykola Yashchenko
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
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

    @Override
    public void modify(final String id, final ModifyUserRequest request, final SnPrincipal principal) {
        // todo call storage service
        if (!Objects.equals(id, principal.userId)) {
            throw new SnException(UserServiceExceptionDetails.CANNOT_MODIFY_USER);
        }

        final int count = userRepository.modify(id, request.getAvatarId());
        if (count == 0) {
            throw new SnException(UserServiceExceptionDetails.NOT_FOUND, User.class.getSimpleName());
        }
    }
}
