package ua.social.network.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.social.network.dto.PostDto;
import ua.social.network.exception.EntityNotFoundException;
import ua.social.network.query.PostMapper;

import java.util.List;
import java.util.Map;

/**
 * @author Mykola Yashchenko
 */
@RestController
@RequestMapping("/users/posts")
public class UserPostController extends AbstractController<PostDto, PostMapper> {

    @Autowired
    public UserPostController(PostMapper mapper) {
        super(mapper);
    }

    @GetMapping("/{id}")
    public PostDto get(@PathVariable("id") String id) {
        PostDto entity = getEntity(id);
        if (entity == null) {
            throw new EntityNotFoundException("User with id %s doesn't exist", id);
        }
        return entity;
    }

    @GetMapping
    public List<PostDto> getList(@RequestParam Map<String, Object> requestParams) {
        return getEntityList(requestParams);
    }
}
