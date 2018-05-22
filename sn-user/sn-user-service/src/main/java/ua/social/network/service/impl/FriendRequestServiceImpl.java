package ua.social.network.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import ua.social.network.dto.AddFriendRequest;
import ua.social.network.entity.BaseEntity;
import ua.social.network.entity.FriendRequest;
import ua.social.network.entity.User;
import ua.social.network.exception.SnException;
import ua.social.network.exception.UserServiceExceptionDetails;
import ua.social.network.oauth2.principal.SnPrincipal;
import ua.social.network.repository.FriendRequestRepository;
import ua.social.network.repository.UserRepository;
import ua.social.network.service.FriendRequestService;

/**
 * @author Mykola Yashchenko
 */
@Service
@AllArgsConstructor
public class FriendRequestServiceImpl implements FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public String send(final AddFriendRequest request, final SnPrincipal user) {
        final Optional<User> newFriendOpt = userRepository.findById(request.getUserId());
        if (!newFriendOpt.isPresent()) {
            throw new SnException(UserServiceExceptionDetails.NOT_FOUND, User.class.getSimpleName(), request.getUserId());
        }

        final User newFriend = newFriendOpt.get();
        return userRepository.findById(user.userId)
                .filter(currentUser -> !currentUser.friendOf(newFriend))
                .map(currentUser -> new FriendRequest(currentUser, newFriend))
                .map(friendRequestRepository::save)
                .map(BaseEntity::getId)
                .orElseThrow(() -> new SnException(UserServiceExceptionDetails.FRIEND_REQUEST_HAS_SENT_ALREADY));
    }

    @Override
    @Transactional
    public void accept(final String id, final SnPrincipal user) {
        final FriendRequest friendRequest = friendRequestRepository.findByIdAndToId(id, user.userId);

        if (friendRequest == null) {
            throw new SnException(UserServiceExceptionDetails.NOT_FOUND, FriendRequest.class.getSimpleName(), id);
        }

        final User to = friendRequest.getTo();
        final User from = friendRequest.getFrom();

        to.addFriend(from);
        from.addFriend(to);

        friendRequestRepository.delete(friendRequest);
    }

    @Override
    @Transactional
    public void deny(final String id, final SnPrincipal user) {
        final Long removedCount = friendRequestRepository.deleteByIdAndToIdOrIdAndFromId(id, user.userId);
        if (removedCount == 0L) {
            throw new SnException(UserServiceExceptionDetails.NOT_FOUND, FriendRequest.class.getSimpleName(), id);
        }
    }
}
