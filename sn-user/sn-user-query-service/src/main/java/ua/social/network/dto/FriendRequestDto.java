package ua.social.network.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Mykola Yashchenko
 */
@Getter
@Setter
public class FriendRequestDto {
    private String id;
    private UserDto from;
    private String date;
}
