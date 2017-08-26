package ua.social.network.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.social.network.dto.FriendRequestDto;
import ua.social.network.query.FriendRequestMapper;

/**
 * @author Mykola Yashchenko
 */
@RestController
@RequestMapping("friend_requests")
public class FriendRequestController extends AbstractController<FriendRequestDto, FriendRequestMapper> {

    public FriendRequestController(FriendRequestMapper mapper) {
        super(mapper);
    }

    @GetMapping
    public List<FriendRequestDto> getList(Principal principal) {
        return getEntityList(Collections.singletonMap("current_user", principal.getName()));
    }
}
