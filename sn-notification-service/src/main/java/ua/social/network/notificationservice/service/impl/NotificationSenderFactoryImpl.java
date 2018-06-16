package ua.social.network.notificationservice.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ua.social.network.notificationservice.api.Payload;
import ua.social.network.notificationservice.service.NotificationSender;
import ua.social.network.notificationservice.service.NotificationSenderFactory;

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
