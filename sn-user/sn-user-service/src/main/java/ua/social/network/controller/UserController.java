package ua.social.network.controller;

import java.security.Principal;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.social.network.dto.AcceptFriendRequest;
import ua.social.network.dto.AddFriendRequest;
import ua.social.network.dto.CreateUserRequest;
import ua.social.network.entity.FriendRequest;
import ua.social.network.entity.Role;
import ua.social.network.entity.User;
import ua.social.network.exception.AccessDeniedException;
import ua.social.network.repository.FriendRequestRepository;
import ua.social.network.repository.UserRepository;
import ua.social.network.transaction.TransactionHelper;

/**
 * @author Mykola Yashchenko
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final TransactionHelper transactionHelper;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserController(UserRepository userRepository, FriendRequestRepository friendRequestRepository,
                          TransactionHelper transactionHelper) {
        this.userRepository = userRepository;
        this.friendRequestRepository = friendRequestRepository;
        this.transactionHelper = transactionHelper;
    }

    @PostMapping
    public void createUser(@Valid @RequestBody CreateUserRequest createUserRequest, Principal principal) {
        if (principal != null) {
            throw new AccessDeniedException("Only unauthorized user can do registration");
        }
        // todo use mapper
        User user = new User();
        user.setEmail(createUserRequest.getEmail());
        user.setName(createUserRequest.getName());
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    //@PreAuthorize("#oauth2.hasScope('ui')")
    @PostMapping("/add_friend")
    public void addFriend(@Valid @RequestBody AddFriendRequest addFriendRequest, Principal principal) {
        if (principal == null) {
            throw new AccessDeniedException("Only authorized user can add an another user to the friend list");
        }

        User newFriend = userRepository.getOne(addFriendRequest.getUserId());
        User currentUser = userRepository.findByEmail(principal.getName());

        FriendRequest friendRequest = friendRequestRepository.findRequestByUsers(newFriend, currentUser);
        if (friendRequest != null) {
            throw new AccessDeniedException("You have this friend request already");
        }

        friendRequest = new FriendRequest();
        friendRequest.setTo(newFriend);
        friendRequest.setFrom(currentUser);

        friendRequestRepository.save(friendRequest);
    }

    //@PreAuthorize("#oauth2.hasScope('ui')")
    @PostMapping("/accept_friend_request")
    public void acceptFriendRequest(@Valid @RequestBody AcceptFriendRequest acceptFriendRequest,
                                    Principal principal) {
        if (principal == null) {
            throw new AccessDeniedException("Only authorized user can accept a friend request");
        }

        transactionHelper.doInTransaction(() -> {
            User currentUser = userRepository.findByEmail(principal.getName());

            FriendRequest friendRequest = friendRequestRepository.findByIdAndTo(
                    acceptFriendRequest.getFriendRequestId(), currentUser);

            User to = friendRequest.getTo();
            User from = friendRequest.getFrom();

            to.addFriend(from);
            to.addFriendOf(from);

            from.addFriend(to);
            from.addFriendOf(to);

            friendRequestRepository.delete(friendRequest);
            return null;
        });
    }
}