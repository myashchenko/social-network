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
import ua.social.network.repository.UserPostRepository;
import ua.social.network.repository.UserRepository;

/**
 * @author Mykola Yashchenko
 */
@RestController
@RequestMapping("users/posts")
@AllArgsConstructor
public class UserPostController {

    private final UserPostRepository userPostRepository;
    private final UserRepository userRepository;

    @PostMapping
    public void create(Principal principal, @Valid @RequestBody CreatePostRequest createPostRequest) {
        Post post = new Post();
        post.setText(createPostRequest.getText());
        Optional<User> receiver = userRepository.findById(createPostRequest.getReceiverId());
        if (!receiver.isPresent()) {
            throw new EntityNotFoundException("User with id %s does not exist", createPostRequest.getReceiverId());
        }
        receiver.ifPresent(post::setTo);
        post.setFrom(userRepository.findByEmail(principal.getName()).get());

        userPostRepository.save(post);
    }

    @PutMapping("/{id}")
    public void modify(@PathVariable("id") String id, Principal principal,
                       @Valid @RequestBody ModifyPostRequest modifyPostRequest) {
        Optional<Post> postOpt = userPostRepository.findById(id);
        if (!postOpt.isPresent()) {
            throw new EntityNotFoundException("Post with id %s does not exist", id);
        }

        Post post = postOpt.get();
        User sender = post.getFrom();
        if (!sender.getEmail().equals(principal.getName())) {
            throw new AccessDeniedException();
        }

        post.setText(modifyPostRequest.getText());

        userPostRepository.save(post);
    }
}
