package ua.social.network.notification.domain;

import ua.social.network.notification.api.Payload;

/**
 * @author Mykola Yashchenko
 */
public class Notification {
    public final Payload payload;

    public Notification(final Payload payload) {
        this.payload = payload;
    }
}
