package ua.social.network.notification.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import it.ozimov.springboot.mail.service.TemplateService;
import lombok.AllArgsConstructor;
import ua.social.network.notification.api.EmailPayload;
import ua.social.network.notification.domain.Notification;
import ua.social.network.notification.service.NotificationSender;

/**
 * @author Mykola Yashchenko
 */
@Service
@AllArgsConstructor
@Profile("!production")
public class SMTPNotificationSender implements NotificationSender<EmailPayload> {

    private final TemplateService templateService;

    @Override
    public void send(final Notification notification) {

    }

    @Override
    public Class<EmailPayload> payloadType() {
        return EmailPayload.class;
    }
}
