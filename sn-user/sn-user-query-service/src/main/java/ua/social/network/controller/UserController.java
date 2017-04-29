package ua.social.network.controller;

import org.springframework.web.bind.annotation.*;
import ua.social.network.dto.UserDto;
import ua.social.network.exception.EntityNotFoundException;
import ua.social.network.query.UserMapper;

import java.util.List;
import java.util.Map;

/**
 * @author Mykola Yashchenko
 */
@RestController
@RequestMapping("/users")
public class UserController extends AbstractController<UserDto, UserMapper> {

    public UserController(UserMapper mapper) {
        super(mapper);
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") String id, @RequestParam Map<String, Object> requestParams) {
        UserDto entity = getEntity(id, requestParams);
        if (entity == null) {
            throw new EntityNotFoundException("User with id %s doesn't exist", id);
        }
        return entity;
    }

    @GetMapping
    public List<UserDto> getUsers(@RequestParam Map<String, Object> requestParams) {
        return getEntityList(requestParams);
    }
}
