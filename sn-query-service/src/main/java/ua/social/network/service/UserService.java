package ua.social.network.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.social.network.dto.UserDto;
import ua.social.network.query.UserMapper;

/**
 * @author Mykola Yashchenko
 */
@RestController
@RequestMapping("/users")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService extends AbstractService<UserDto, UserMapper> {

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") String id) {
        return getEntity(id);
    }
}
