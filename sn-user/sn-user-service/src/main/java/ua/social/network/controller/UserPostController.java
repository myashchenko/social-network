package ua.social.network.controller;

import java.security.Principal;
import java.util.Optional;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ua.social.network.dto.CreatePostRequest;
import ua.social.network.dto.ModifyPostRequest;
import ua.social.network.entity.Post;
import ua.social.network.entity.User;
import ua.social.network.exception.AccessDeniedException;
import ua.social.network.exception.EntityNotFoundException;
import ua.social.network.oauth2.principal.SnPrincipal;
import ua.social.network.repository.UserPostRepository;
import ua.social.network.repository.UserRepository;
import ua.social.network.service.UserPostService;

/**
 * @author Mykola Yashchenko
 */
@RestController
@RequestMapping("users/posts")
@AllArgsConstructor
public class UserPostController {

    private final UserPostService userPostService;

    @PostMapping
    public void create(@Valid @RequestBody final CreatePostRequest request, final Principal principal) {
        userPostService.create(request, new SnPrincipal(principal));
    }

    @PutMapping("/{id}")
    public void modify(@PathVariable("id") final String id, @Valid @RequestBody final ModifyPostRequest request,
                       final Principal principal) {
        userPostService.modify(id, request, new SnPrincipal(principal));
    }
}
