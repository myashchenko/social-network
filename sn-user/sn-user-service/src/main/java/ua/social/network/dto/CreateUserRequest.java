package ua.social.network.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Mykola Yashchenko
 */
@Getter
@Setter
public class CreateUserRequest {
    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 5)
    private String password;
}
