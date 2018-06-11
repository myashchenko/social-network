package ua.social.network.queue.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Mykola Yashchenko
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SQSConfigurationProperties {
    private String queueName;
    private String accessKey;
    private String secretKey;
    private String region;
    private String endpoint;
}
