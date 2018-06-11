package ua.social.network.notification.service;

import ua.social.network.notification.api.Payload;

/**
 * @author Mykola Yashchenko
 */
public interface NotificationSenderFactory {
    <T extends Payload> NotificationSender<T> build(Class<T> event);
}
