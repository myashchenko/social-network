package ua.social.network.userservice.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Mykola Yashchenko
 */
@Getter
@Setter
public class CreatePostRequest {
    @NotBlank
    private String text;
    @NotBlank
    private String receiverId;
}
