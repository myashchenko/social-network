package ua.social.network.userservice.controller;

import java.security.Principal;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ua.social.network.oauth2.principal.SnPrincipal;
import ua.social.network.userservice.dto.AddFriendRequest;
import ua.social.network.userservice.service.FriendRequestService;

/**
 * @author Mykola Yashchenko
 */
@RestController
@AllArgsConstructor
@RequestMapping("/friend_requests")
public class FriendRequestController {

    private final FriendRequestService friendRequestService;

    @PostMapping
    @PreAuthorize("#oauth2.hasScope('ui')")
    public void addFriend(@Valid @RequestBody final AddFriendRequest request, final Principal principal) {
        friendRequestService.send(request, new SnPrincipal(principal));
    }

    @PostMapping("/{id}")
    @PreAuthorize("#oauth2.hasScope('ui')")
    public void acceptFriendRequest(@PathVariable final String id, final Principal principal) {
        friendRequestService.accept(id, new SnPrincipal(principal));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("#oauth2.hasScope('ui')")
    public void denyFriendRequest(@PathVariable final String id, final Principal principal) {
        friendRequestService.deny(id, new SnPrincipal(principal));
    }
}
