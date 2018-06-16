package ua.social.network.notificationservice.service;

import ua.social.network.notificationservice.api.Payload;

/**
 * @author Mykola Yashchenko
 */
public interface NotificationSenderFactory {
    <T extends Payload> NotificationSender<T> build(Class<T> event);
}
