package ua.social.network.notification.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Mykola Yashchenko
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "service.ses")
public class SESProperties {
    private String emailFrom;
    private String accessKey;
    private String secretKey;
    private String region;
}
