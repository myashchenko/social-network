package ua.social.network.service;

import ua.social.network.dto.AddFriendRequest;
import ua.social.network.oauth2.principal.SnPrincipal;

/**
 * @author Mykola Yashchenko
 */
public interface FriendRequestService {
    String send(AddFriendRequest request, SnPrincipal user);
    void accept(String id, SnPrincipal user);
    void deny(String id, SnPrincipal user);
}
