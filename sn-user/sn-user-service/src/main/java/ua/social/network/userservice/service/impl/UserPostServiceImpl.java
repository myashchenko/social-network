package ua.social.network.userservice.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import ua.social.network.exception.SnException;
import ua.social.network.exception.UserServiceExceptionDetails;
import ua.social.network.oauth2.principal.SnPrincipal;
import ua.social.network.userservice.dto.CreatePostRequest;
import ua.social.network.userservice.dto.ModifyPostRequest;
import ua.social.network.userservice.entity.Post;
import ua.social.network.userservice.entity.User;
import ua.social.network.userservice.repository.UserPostRepository;
import ua.social.network.userservice.repository.UserRepository;
import ua.social.network.userservice.service.UserPostService;

/**
 * @author Mykola Yashchenko
 */
@Service
@AllArgsConstructor
public class UserPostServiceImpl implements UserPostService {

    private final UserRepository userRepository;
    private final UserPostRepository userPostRepository;

    @Override
    @Transactional
    public String create(final CreatePostRequest request, final SnPrincipal user) {
        final Post post = new Post();
        post.setText(request.getText());
        final Optional<User> receiver = userRepository.findById(request.getReceiverId());
        if (!receiver.isPresent()) {
            throw new SnException(UserServiceExceptionDetails.NOT_FOUND, User.class.getSimpleName());
        }
        receiver.ifPresent(post::setTo);
        post.setFrom(userRepository.findById(user.userId).get());

        return userPostRepository.save(post).getId();
    }

    @Override
    @Transactional
    public void modify(final String id, final ModifyPostRequest request, final SnPrincipal user) {
        userPostRepository.findByIdAndFromId(id, user.userId)
                .ifPresentOrElse(post -> {
                    post.setText(request.getText());
                    userPostRepository.save(post);
                }, () -> {
                    throw new SnException(UserServiceExceptionDetails.NOT_FOUND, Post.class.getSimpleName());
                });
    }
}
