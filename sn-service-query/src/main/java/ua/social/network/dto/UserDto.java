package ua.social.network.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author Mykola Yashchenko
 */
@Getter
@Setter
@ToString(of = { "name", "lastVisit", "avatar", "friends" })
public class UserDto {
    private String name;
    private String lastVisit;
    private String avatar;
    private List<UserDto> friends;
}
