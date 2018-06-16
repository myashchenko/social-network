package ua.social.network.notificationservice.listener.impl;

import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.social.network.notificationservice.api.Payload;
import ua.social.network.notificationservice.domain.Notification;
import ua.social.network.notificationservice.listener.QueueListener;
import ua.social.network.notificationservice.service.NotificationSenderFactory;
import ua.social.network.queue.service.QueueService;

/**
 * @author Mykola Yashchenko
 */
@Slf4j
@Service
@AllArgsConstructor
public class NotificationQueueListenerImpl implements QueueListener {

    private static final Gson GSON = new Gson();

    private final QueueService notificationQueueService;
    private final NotificationSenderFactory notificationSenderFactory;

    @Override
    public void listen() {
        while (true) {
            try {
                notificationQueueService.receive()
                        .map(message -> {
                            final String payload = (String) message.getPayload();
                            final String className = message.getClassName();

                            try {
                                return (Payload) GSON.fromJson(payload, Class.forName(className));
                            } catch (final ClassNotFoundException e) {
                                throw new RuntimeException(e.getMessage(), e);
                            }
                        })
                        .forEach(payload -> {
                            notificationSenderFactory.build(payload.getClass())
                                    .send(new Notification(payload));
                        });
            } catch (final Exception e) {
                log.error(e.getMessage());

                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e1) {
                    log.error(e1.getMessage(), e1);
                }
            }
        }
    }
}
