package ua.social.network.query;

import ua.social.network.dto.UserDto;

import java.util.Map;
import java.util.Set;

/**
 * @author Mykola Yashchenko
 */
public interface UserMapper {
    Set<UserDto> getFriends(Map<String, Object> params);
    UserDto getUser(Map<String, Object> params);
}
