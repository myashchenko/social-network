package ua.social.network.userservice.service;

import ua.social.network.oauth2.principal.SnPrincipal;
import ua.social.network.storage.domain.FileMetadata;
import ua.social.network.userservice.dto.CreateUserRequest;
import ua.social.network.userservice.dto.ModifyUserRequest;
import ua.social.network.userservice.dto.UpdateAvatarResponse;

/**
 * @author Mykola Yashchenko
 */
public interface UserService {
    String create(CreateUserRequest request);
    void modify(ModifyUserRequest request, SnPrincipal principal);
    UpdateAvatarResponse updateAvatar(FileMetadata fileMetadata, SnPrincipal principal);
}
