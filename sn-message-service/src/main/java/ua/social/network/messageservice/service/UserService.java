package ua.social.network.messageservice.service;

import ua.social.network.messageservice.api.user.UserEvent;

/**
 * @author Mykola Yashchenko
 */
public interface UserService {
    void processEvent(UserEvent event);
}
