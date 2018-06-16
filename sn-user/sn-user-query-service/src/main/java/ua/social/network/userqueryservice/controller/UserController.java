package ua.social.network.userqueryservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.social.network.controller.AbstractController;
import ua.social.network.exception.SnException;
import ua.social.network.exception.UserQueryServiceExceptionDetails;
import ua.social.network.userqueryservice.dto.UserDto;
import ua.social.network.userqueryservice.query.UserMapper;

/**
 * @author Mykola Yashchenko
 */
@RestController
@RequestMapping("/users")
public class UserController extends AbstractController<UserDto, UserMapper> {

    public UserController(final UserMapper mapper) {
        super(mapper);
    }

    @GetMapping("/{id}")
    @PreAuthorize("#oauth2.hasScope('ui')")
    public UserDto getUser(@PathVariable("id") final String id, @RequestParam final Map<String, Object> requestParams) {
        final UserDto entity = getEntity(id, requestParams);
        if (entity == null) {
            throw new SnException(UserQueryServiceExceptionDetails.NOT_FOUND);
        }
        return entity;
    }

    @GetMapping
    @PreAuthorize("#oauth2.hasScope('ui')")
    public List<UserDto> getUsers(@RequestParam final Map<String, Object> requestParams) {
        return getEntityList(requestParams);
    }
}
