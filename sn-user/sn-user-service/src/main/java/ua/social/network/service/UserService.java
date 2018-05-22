package ua.social.network.service;

import ua.social.network.dto.CreateUserRequest;

/**
 * @author Mykola Yashchenko
 */
public interface UserService {
    String create(CreateUserRequest request);
}
