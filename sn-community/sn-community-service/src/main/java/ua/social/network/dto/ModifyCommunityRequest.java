package ua.social.network.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Mykola Yashchenko
 */
@Getter
@Setter
public class ModifyCommunityRequest {
    @NotBlank
    private String name;
    private String description;
}
