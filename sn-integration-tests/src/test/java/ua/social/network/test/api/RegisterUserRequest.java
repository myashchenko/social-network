package ua.social.network.test.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mykola Yashchenko
 */
@Getter
@AllArgsConstructor
public class RegisterUserRequest {
    private final String email;
    private final String password;
    private final String name;
}
