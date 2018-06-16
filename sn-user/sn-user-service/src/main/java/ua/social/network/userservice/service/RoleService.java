package ua.social.network.userservice.service;

import ua.social.network.userservice.dto.AssignRoleRequest;

/**
 * @author Mykola Yashchenko
 */
public interface RoleService {
    void assign(AssignRoleRequest request);
}
