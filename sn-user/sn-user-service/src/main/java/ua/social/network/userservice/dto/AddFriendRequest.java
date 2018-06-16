package ua.social.network.userservice.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Mykola Yashchenko
 */
@Getter
@Setter
public class AddFriendRequest {
    @NotEmpty
    private String userId;
}
