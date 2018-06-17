package ua.social.network.notificationservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;
import ua.social.network.notificationservice.properties.SQSProperties;
import ua.social.network.queue.service.QueueService;
import ua.social.network.queue.service.impl.SQSService;

/**
 * @author Mykola Yashchenko
 */
@Slf4j
@Configuration
public class QueueConfig {

    @Bean
    public QueueService notificationQueueService(final SQSProperties properties) {
        log.info("Creating queue: {}", properties);
        return new SQSService(properties);
    }
}
