package ua.social.network.message.api.user;

import lombok.Data;

/**
 * @author Mykola Yashchenko
 */
@Data
public class UserEvent {
    private String id;
    private String name;
    private String avatar;
    private UserEventType type;
}
