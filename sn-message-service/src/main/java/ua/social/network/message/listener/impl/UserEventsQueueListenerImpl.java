package ua.social.network.message.listener.impl;

import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.social.network.message.api.user.UserEvent;
import ua.social.network.message.listener.QueueListener;
import ua.social.network.message.service.UserService;
import ua.social.network.queue.service.QueueService;

/**
 * @author Mykola Yashchenko
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserEventsQueueListenerImpl implements QueueListener {

    private static final Gson GSON = new Gson();

    private final QueueService userEventsQueueService;
    private final UserService userService;

    @Override
    public void listen() {
        while (true) {
            try {
                userEventsQueueService.receive()
                        .map(message -> {
                            final String payload = (String) message.getPayload();
                            return GSON.fromJson(payload, UserEvent.class);
                        })
                        .forEach(userService::processEvent);
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
