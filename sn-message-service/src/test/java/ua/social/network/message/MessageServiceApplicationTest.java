package ua.social.network.message;

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
@DockerCompose(
        logs = "build/logs/docker-compose",
        waitFor = {
                @WaitFor(
                        service = "cassandra",
                        healthCheck = @HealthCheck(
                                type = HealthCheckType.ALL_PORTS_OPEN
                        )
                ),
                @WaitFor(
                        service = "sqs",
                        healthCheck = @HealthCheck(
                                type = HealthCheckType.ALL_PORTS_OPEN
                        )
                )
        }
)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MessageServiceApplication.class)
public class MessageServiceApplicationTest {

    @Test
    public void shouldLoadContext() {

    }
}
