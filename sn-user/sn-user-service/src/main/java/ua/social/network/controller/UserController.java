package ua.social.network.controller;

import java.security.Principal;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ua.social.network.dto.CreateUserRequest;
import ua.social.network.dto.ModifyUserRequest;
import ua.social.network.oauth2.principal.SnPrincipal;
import ua.social.network.service.UserService;

/**
 * @author Mykola Yashchenko
 */
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @PreAuthorize("#oauth2.hasScope('ui')")
    public void createUser(@Valid @RequestBody final CreateUserRequest createUserRequest) {
        userService.create(createUserRequest);
    }

    @PutMapping("/{id}")
    @PreAuthorize("#oauth2.hasScope('ui')")
    public void modifyUser(@PathVariable("id") final String id, final ModifyUserRequest request,
                           final Principal principal) {
        userService.modify(id, request, new SnPrincipal(principal));
    }
}