package ua.social.network.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Mykola Yashchenko
 */
@Getter
@Setter
public class ModifyUserRequest {
    @NotEmpty(message = "SN-001")
    private String name;
}
