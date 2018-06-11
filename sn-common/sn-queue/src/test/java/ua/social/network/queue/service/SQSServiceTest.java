package ua.social.network.queue.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.regions.Regions;
import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.connection.Container;

import org.junit.jupiter.api.Test;

import io.github.yashchenkon.dockercompose.junit5.annotation.DockerCompose;
import io.github.yashchenkon.dockercompose.junit5.annotation.HealthCheck;
import io.github.yashchenkon.dockercompose.junit5.annotation.WaitFor;
import io.github.yashchenkon.dockercompose.junit5.constant.HealthCheckType;
import ua.social.network.queue.api.Message;
import ua.social.network.queue.properties.SQSConfigurationProperties;
import ua.social.network.queue.service.impl.SQSService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Mykola Yashchenko
 */
@DockerCompose(
        logs = "build/docker-compose",
        waitFor = @WaitFor(service = "sqs", healthCheck = @HealthCheck(type = HealthCheckType.TO_RESPOND_OVER_HTTP, port = 9324))
)
public class SQSServiceTest {

    static {
        System.setProperty(SDKGlobalConfiguration.DISABLE_CERT_CHECKING_SYSTEM_PROPERTY, "true");
    }

    @Test
    public void shouldWriteRead(final DockerComposeRule dockerComposeRule) {
        final String sqs = dockerComposeRule.containers().container("sqs").port(9324).inFormat("http://$HOST:$EXTERNAL_PORT");
        final QueueService queueService = new SQSService(
                new SQSConfigurationProperties("notification", "x", "x", Regions.EU_CENTRAL_1.getName(), sqs)
        );

        final String payload = UUID.randomUUID().toString();
        queueService.send(new Message(payload));

        final List<Message> messages = queueService.receive().collect(Collectors.toList());
        assertThat(messages, hasSize(1));

        final Message message = messages.get(0);
        assertThat(message, notNullValue());
        assertThat(message.getPayload(), equalTo(payload));
    }
}
