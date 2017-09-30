package ua.social.network.controller;

import java.security.Principal;
import java.util.Optional;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.social.network.dto.AddFriendRequest;
import ua.social.network.entity.FriendRequest;
import ua.social.network.entity.User;
import ua.social.network.exception.AccessDeniedException;
import ua.social.network.exception.EntityNotFoundException;
import ua.social.network.repository.FriendRequestRepository;
import ua.social.network.repository.UserRepository;
import ua.social.network.transaction.TransactionHelper;

/**
 * @author Mykola Yashchenko
 */
@RestController
@RequestMapping("/friend_requests")
public class FriendRequestController {

    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final TransactionHelper transactionHelper;

    public FriendRequestController(UserRepository userRepository, FriendRequestRepository friendRequestRepository,
                                   TransactionHelper transactionHelper) {
        this.userRepository = userRepository;
        this.friendRequestRepository = friendRequestRepository;
        this.transactionHelper = transactionHelper;
    }

    //@PreAuthorize("#oauth2.hasScope('ui')")
    @PostMapping
    public void addFriend(@Valid @RequestBody AddFriendRequest addFriendRequest, Principal principal) {
        if (principal == null) {
            throw new AccessDeniedException("Only authorized user can add an another user to the friend list");
        }

        transactionHelper.doInTransaction(() -> {
            Optional<User> newFriendOpt = userRepository.findById(addFriendRequest.getUserId());
            if (!newFriendOpt.isPresent()) {
                throw new EntityNotFoundException("User with id %s does not exist", addFriendRequest.getUserId());
            }
            User newFriend = newFriendOpt.get();
            User currentUser = userRepository.findByEmail(principal.getName()).get();

            if (currentUser.friendOf(newFriend)) {
                throw new AccessDeniedException("You are friends already");
            }

            FriendRequest friendRequest = new FriendRequest();
            friendRequest.setTo(newFriend);
            friendRequest.setFrom(currentUser);

            friendRequestRepository.save(friendRequest);
            return null;
        });
    }

    //@PreAuthorize("#oauth2.hasScope('ui')")
    @PostMapping("/{id}")
    public void acceptFriendRequest(@PathVariable String id, Principal principal) {
        if (principal == null) {
            throw new AccessDeniedException("Only authorized user can accept a friend request");
        }

        transactionHelper.doInTransaction(() -> {
            User currentUser = userRepository.findByEmail(principal.getName()).get();

            FriendRequest friendRequest = friendRequestRepository.findByIdAndTo(id, currentUser);

            if (friendRequest == null) {
                throw new EntityNotFoundException("Friend request with id = %s does not exist", id);
            }

            User to = friendRequest.getTo();
            User from = friendRequest.getFrom();

            to.addFriend(from);
            from.addFriend(to);

            friendRequestRepository.delete(friendRequest);
            return null;
        });
    }
}
