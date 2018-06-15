package ua.social.network.message.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ua.social.network.message.properties.SQSProperties;
import ua.social.network.queue.service.QueueService;
import ua.social.network.queue.service.impl.SQSService;

/**
 * @author Mykola Yashchenko
 */
@Configuration
public class QueueConfig {

    @Bean
    public QueueService userEventsQueueService(final SQSProperties properties) {
        return new SQSService(properties);
    }
}
