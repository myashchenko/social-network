package ua.social.network.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.social.network.dto.PostDto;
import ua.social.network.exception.SnException;
import ua.social.network.exception.UserQueryServiceExceptionDetails;
import ua.social.network.oauth2.principal.SnPrincipal;
import ua.social.network.query.PostMapper;

/**
 * @author Mykola Yashchenko
 */
@RestController
@RequestMapping("/posts")
public class UserPostController extends AbstractController<PostDto, PostMapper> {

    private static final String USER_ID = "user_id";

    public UserPostController(final PostMapper mapper) {
        super(mapper);
    }

    @GetMapping("/{id}")
    @PreAuthorize("#oauth2.hasScope('ui')")
    public PostDto get(@PathVariable("id") final String id, @RequestParam final Map<String, Object> requestParams) {
        final PostDto entity = getEntity(id, requestParams);
        if (entity == null) {
            throw new SnException(UserQueryServiceExceptionDetails.NOT_FOUND, "Post");
        }
        return entity;
    }

    @GetMapping
    @PreAuthorize("#oauth2.hasScope('ui')")
    public List<PostDto> getList(final Principal principal, @RequestParam final Map<String, Object> requestParams) {
        requestParams.putIfAbsent(USER_ID, new SnPrincipal(principal).userId);
        return getEntityList(requestParams);
    }
}
