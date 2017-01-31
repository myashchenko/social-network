package ua.social.network.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Mykola Yashchenko
 */
@Getter
@Setter
public class ModifyUserRequest {
    private String email;
    private String name;
}
