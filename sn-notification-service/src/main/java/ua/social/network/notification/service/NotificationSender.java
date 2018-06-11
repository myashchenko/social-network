package ua.social.network.notification.service;

import ua.social.network.notification.api.Payload;
import ua.social.network.notification.domain.Notification;

/**
 * @author Mykola Yashchenko
 */
public interface NotificationSender<T extends Payload> {
    void send(Notification notification);

    Class<T> payloadType();
}
