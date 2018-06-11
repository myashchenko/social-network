package ua.social.network.notification.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import ua.social.network.notification.api.SmsPayload;
import ua.social.network.notification.domain.Notification;
import ua.social.network.notification.service.NotificationSender;

/**
 * @author Mykola Yashchenko
 */
@Service
@Profile("!production")
public class SmsNoOpNotificationSender implements NotificationSender<SmsPayload> {

    @Override
    public void send(final Notification notification) {

    }

    @Override
    public Class<SmsPayload> payloadType() {
        return SmsPayload.class;
    }
}
