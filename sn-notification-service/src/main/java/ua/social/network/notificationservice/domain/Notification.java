package ua.social.network.notificationservice.domain;

import ua.social.network.notificationservice.api.Payload;

/**
 * @author Mykola Yashchenko
 */
public class Notification {
    public final Payload payload;

    public Notification(final Payload payload) {
        this.payload = payload;
    }
}
