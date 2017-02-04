package ua.social.network.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.social.network.dto.UserDto;
import ua.social.network.query.UserMapper;

import java.util.List;

/**
 * @author Mykola Yashchenko
 */
@RestController
@RequestMapping("/users")
public class UserService extends AbstractService<UserDto, UserMapper> {

    @Autowired
    public UserService(UserMapper mapper) {
        super(mapper);
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") String id) {
        return getEntity(id);
    }

    @GetMapping
    public List<UserDto> getUsers() {
        // todo
        return getEntityList(new Object());
    }

    @GetMapping("/{id}/friends")
    public List<UserDto> getUserFriends() {
        // todo
        return getEntityList(new Object());
    }
}
