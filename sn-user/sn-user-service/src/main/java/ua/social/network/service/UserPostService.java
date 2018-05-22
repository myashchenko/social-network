package ua.social.network.service;

import ua.social.network.dto.CreatePostRequest;
import ua.social.network.dto.ModifyPostRequest;
import ua.social.network.oauth2.principal.SnPrincipal;

/**
 * @author Mykola Yashchenko
 */
public interface UserPostService {
    String create(CreatePostRequest request, SnPrincipal user);
    void modify(String id, ModifyPostRequest request, SnPrincipal user);
}
