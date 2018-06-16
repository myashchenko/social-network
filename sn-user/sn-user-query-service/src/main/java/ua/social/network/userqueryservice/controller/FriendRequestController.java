package ua.social.network.userqueryservice.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.social.network.controller.AbstractController;
import ua.social.network.oauth2.principal.SnPrincipal;
import ua.social.network.userqueryservice.dto.FriendRequestDto;
import ua.social.network.userqueryservice.query.FriendRequestMapper;

/**
 * @author Mykola Yashchenko
 */
@RestController
@RequestMapping("/friend_requests")
public class FriendRequestController extends AbstractController<FriendRequestDto, FriendRequestMapper> {

    public FriendRequestController(final FriendRequestMapper mapper) {
        super(mapper);
    }

    @GetMapping
    @PreAuthorize("#oauth2.hasScope('ui')")
    public List<FriendRequestDto> getList(final Principal principal) {
        return getEntityList(Collections.singletonMap("current_user_id", new SnPrincipal(principal).userId));
    }
}
