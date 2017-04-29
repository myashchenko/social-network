package ua.social.network.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.social.network.dto.CreatePostRequest;
import ua.social.network.dto.ModifyPostRequest;
import ua.social.network.entity.Post;
import ua.social.network.entity.User;
import ua.social.network.exception.AccessDeniedException;
import ua.social.network.exception.EntityNotFoundException;
import ua.social.network.repository.UserPostRepository;
import ua.social.network.repository.UserRepository;

import javax.validation.Valid;
import java.security.Principal;

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
        User receiver = userRepository.findOne(createPostRequest.getReceiverId());
        if (receiver == null) {
            throw new EntityNotFoundException("User with id %s does not exist", createPostRequest.getReceiverId());
        }
        post.setReceiver(receiver);
        post.setSender(userRepository.findByEmail(principal.getName()));

        userPostRepository.save(post);
    }

    @PutMapping("/{id}")
    public void modify(@PathVariable("id") String id, Principal principal,
                       @Valid @RequestBody ModifyPostRequest modifyPostRequest) {
        Post post = userPostRepository.findOne(id);
        if (post == null) {
            throw new EntityNotFoundException("Post with id %s does not exist", id);
        }

        User sender = post.getSender();
        if (!sender.getEmail().equals(principal.getName())) {
            throw new AccessDeniedException();
        }

        post.setText(modifyPostRequest.getText());

        userPostRepository.save(post);
    }
}
