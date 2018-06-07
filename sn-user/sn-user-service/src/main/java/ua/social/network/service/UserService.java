package ua.social.network.service;

import ua.social.network.dto.CreateUserRequest;
import ua.social.network.dto.ModifyUserRequest;
import ua.social.network.dto.UpdateAvatarResponse;
import ua.social.network.oauth2.principal.SnPrincipal;
import ua.social.network.storage.domain.FileMetadata;

/**
 * @author Mykola Yashchenko
 */
public interface UserService {
    String create(CreateUserRequest request);
    void modify(ModifyUserRequest request, SnPrincipal principal);
    UpdateAvatarResponse updateAvatar(FileMetadata fileMetadata, SnPrincipal principal);
}
