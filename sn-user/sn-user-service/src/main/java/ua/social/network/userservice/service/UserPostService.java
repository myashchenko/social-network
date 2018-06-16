package ua.social.network.userservice.service;

import ua.social.network.oauth2.principal.SnPrincipal;
import ua.social.network.userservice.dto.CreatePostRequest;
import ua.social.network.userservice.dto.ModifyPostRequest;

/**
 * @author Mykola Yashchenko
 */
public interface UserPostService {
    String create(CreatePostRequest request, SnPrincipal user);
    void modify(String id, ModifyPostRequest request, SnPrincipal user);
}
