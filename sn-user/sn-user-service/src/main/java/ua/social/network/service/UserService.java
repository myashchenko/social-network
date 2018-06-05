package ua.social.network.service;

import ua.social.network.dto.CreateUserRequest;
import ua.social.network.dto.ModifyUserRequest;
import ua.social.network.oauth2.principal.SnPrincipal;

/**
 * @author Mykola Yashchenko
 */
public interface UserService {
    String create(CreateUserRequest request);
    void modify(String id, ModifyUserRequest request, SnPrincipal principal);
}
