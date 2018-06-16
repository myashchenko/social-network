package ua.social.network.userservice.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Mykola Yashchenko
 */
@Getter
@Setter
public class ModifyPostRequest {
    @NotBlank
    private String text;
}
