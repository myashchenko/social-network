package ua.social.network.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.social.network.dto.PostDto;
import ua.social.network.exception.EntityNotFoundException;
import ua.social.network.query.PostMapper;

/**
 * @author Mykola Yashchenko
 */
@RestController
@RequestMapping("/users/posts")
public class UserPostController extends AbstractController<PostDto, PostMapper> {

    private static final String USER_NAME = "user_name";

    public UserPostController(PostMapper mapper) {
        super(mapper);
    }

    @GetMapping("/{id}")
    public PostDto get(@PathVariable("id") String id, @RequestParam Map<String, Object> requestParams) {
        PostDto entity = getEntity(id, requestParams);
        if (entity == null) {
            throw new EntityNotFoundException("User with id %s doesn't exist", id);
        }
        return entity;
    }

    @GetMapping
    public List<PostDto> getList(Principal principal, @RequestParam Map<String, Object> requestParams) {
        requestParams.putIfAbsent(USER_NAME, principal.getName());
        return getEntityList(requestParams);
    }
}
