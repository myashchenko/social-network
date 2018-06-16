package ua.social.network.notificationservice.service;

import ua.social.network.notificationservice.api.Payload;
import ua.social.network.notificationservice.domain.Notification;

/**
 * @author Mykola Yashchenko
 */
public interface NotificationSender<T extends Payload> {
    void send(Notification notification);

    Class<T> payloadType();
}
