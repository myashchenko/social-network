package ua.social.network.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Mykola Yashchenko
 */
@Getter
@Setter
public class ModifyPostRequest {
    @NotBlank
    private String text;
}
