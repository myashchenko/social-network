package ua.social.network.notificationservice.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.social.network.queue.properties.SQSConfigurationProperties;

/**
 * @author Mykola Yashchenko
 */
@Getter
@Setter
@Configuration
@ToString(callSuper = true)
@ConfigurationProperties(prefix = "service.sqs")
public class SQSProperties extends SQSConfigurationProperties {
}
