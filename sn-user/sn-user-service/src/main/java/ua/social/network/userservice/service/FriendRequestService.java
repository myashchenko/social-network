package ua.social.network.userservice.service;

import ua.social.network.oauth2.principal.SnPrincipal;
import ua.social.network.userservice.dto.AddFriendRequest;

/**
 * @author Mykola Yashchenko
 */
public interface FriendRequestService {
    String send(AddFriendRequest request, SnPrincipal user);
    void accept(String id, SnPrincipal user);
    void deny(String id, SnPrincipal user);
}
