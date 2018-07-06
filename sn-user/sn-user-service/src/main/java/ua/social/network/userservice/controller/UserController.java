package ua.social.network.userservice.controller;

import java.security.Principal;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import ua.social.network.oauth2.principal.SnPrincipal;
import ua.social.network.storage.domain.FileMetadata;
import ua.social.network.userservice.dto.CreateUserRequest;
import ua.social.network.userservice.dto.ModifyUserRequest;
import ua.social.network.userservice.dto.UpdateAvatarResponse;
import ua.social.network.userservice.service.UserService;

/**
 * @author Mykola Yashchenko
 */
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public void createUser(@Valid @RequestBody final CreateUserRequest createUserRequest) {
        userService.create(createUserRequest);
    }

    @PutMapping
    @PreAuthorize("#oauth2.hasScope('ui')")
    public void modifyUser(@Valid @RequestBody final ModifyUserRequest request, final Principal principal) {
        userService.modify(request, new SnPrincipal(principal));
    }

    @PostMapping("/avatar")
    @PreAuthorize("#oauth2.hasScope('ui')")
    public UpdateAvatarResponse uploadAvatar(@RequestParam("file") final MultipartFile file, final Principal principal) {
        return userService.updateAvatar(new FileMetadata(file), new SnPrincipal(principal));
    }
}