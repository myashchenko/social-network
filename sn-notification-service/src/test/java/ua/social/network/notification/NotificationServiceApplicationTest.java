package ua.social.network.notification;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.yashchenkon.dockercompose.junit5.annotation.DockerCompose;
import io.github.yashchenkon.dockercompose.junit5.annotation.HealthCheck;
import io.github.yashchenkon.dockercompose.junit5.annotation.WaitFor;
import io.github.yashchenkon.dockercompose.junit5.constant.HealthCheckType;

/**
 * @author Mykola Yashchenko
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = NotificationServiceApplication.class)
@DockerCompose(
        logs = "build/docker-compose",
        waitFor = @WaitFor(service = "sqs", healthCheck = @HealthCheck(type = HealthCheckType.TO_RESPOND_OVER_HTTP, port = 9324))
)
public class NotificationServiceApplicationTest {

    @Test
    public void shouldLoadContext() {

    }
}
