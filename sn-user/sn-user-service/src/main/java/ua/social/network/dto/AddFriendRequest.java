package ua.social.network.dto;

import org.hibernate.validator.constraints.NotEmpty;

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
