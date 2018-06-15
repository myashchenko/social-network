package ua.social.network.message.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import ua.social.network.queue.properties.SQSConfigurationProperties;

/**
 * @author Mykola Yashchenko
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "service.sqs")
public class SQSProperties extends SQSConfigurationProperties {
}
