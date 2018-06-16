package ua.social.network.notificationservice.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import ua.social.network.notificationservice.api.SmsPayload;
import ua.social.network.notificationservice.domain.Notification;
import ua.social.network.notificationservice.service.NotificationSender;

/**
 * @author Mykola Yashchenko
 */
@Service
@Profile("production")
public class SNSNotificationSender implements NotificationSender<SmsPayload> {

    @Override
    public void send(final Notification notification) {

    }

    @Override
    public Class<SmsPayload> payloadType() {
        return SmsPayload.class;
    }
}
