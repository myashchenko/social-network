package ua.social.network.message.service;

import ua.social.network.message.api.user.UserEvent;

/**
 * @author Mykola Yashchenko
 */
public interface UserService {
    void processEvent(UserEvent event);
}
