package ua.social.network.service;

import ua.social.network.dto.AssignRoleRequest;

/**
 * @author Mykola Yashchenko
 */
public interface RoleService {
    void assign(AssignRoleRequest request);
}
