package ua.social.network.userservice.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import ua.social.network.exception.SnException;
import ua.social.network.exception.UserServiceExceptionDetails;
import ua.social.network.userservice.dto.AssignRoleRequest;
import ua.social.network.userservice.entity.Role;
import ua.social.network.userservice.entity.User;
import ua.social.network.userservice.repository.UserRepository;
import ua.social.network.userservice.service.RoleService;

/**
 * @author Mykola Yashchenko
 */
@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private UserRepository userRepository;

    @Override
    @Transactional
    public void assign(final AssignRoleRequest request) {
        final long updated = userRepository.updateRole(request.getUserId(), Role.valueOf(request.getRole()));
        if (updated == 0L) {
            throw new SnException(UserServiceExceptionDetails.NOT_FOUND, User.class.getSimpleName());
        }
    }
}
