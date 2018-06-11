package ua.social.network.notification.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ua.social.network.notification.api.Payload;
import ua.social.network.notification.service.NotificationSender;
import ua.social.network.notification.service.NotificationSenderFactory;

/**
 * @author Mykola Yashchenko
 */
@Service
public class NotificationSenderFactoryImpl implements NotificationSenderFactory {

    private final Map<Class<? extends Payload>, NotificationSender<? extends Payload>> senders;

    public NotificationSenderFactoryImpl(final List<NotificationSender<? extends Payload>> senders) {
        this.senders = new HashMap<>();

        for (final NotificationSender<? extends Payload> sender : senders) {
            this.senders.put(sender.payloadType(), sender);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Payload> NotificationSender<T> build(final Class<T> event) {
        return (NotificationSender<T>) senders.get(event);
    }
}
