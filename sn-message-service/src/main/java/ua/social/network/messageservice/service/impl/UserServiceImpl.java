package ua.social.network.messageservice.service.impl;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ua.social.network.messageservice.api.user.UserEvent;
import ua.social.network.messageservice.entity.User;
import ua.social.network.messageservice.repository.UserRepository;
import ua.social.network.messageservice.service.UserService;

/**
 * @author Mykola Yashchenko
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public void processEvent(final UserEvent event) {
        final User user = User.builder()
                .id(event.getId())
                .name(event.getName())
                .avatarUrl(event.getAvatar())
                .build();

        switch (event.getType()) {
            case REGISTER:
            case MODIFY:
                userRepository.insert(user);
                break;
            case DELETE:
                userRepository.deleteById(event.getId());
                break;
        }
    }
}
