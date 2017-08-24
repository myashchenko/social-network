package ua.social.network.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author Mykola Yashchenko
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ToString(of = { "id", "name", "lastVisit", "avatar", "friends", "friendRequestsCount" })
public class UserDto {
    private String id;
    private String name;
    private String lastVisit;
    private String avatar;
    private List<UserDto> friends;
    private Integer friendRequestsCount;
}
