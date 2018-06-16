package ua.social.network.notificationservice.service;

import java.io.IOException;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.yashchenkon.dockercompose.junit5.annotation.DockerCompose;
import io.github.yashchenkon.dockercompose.junit5.annotation.HealthCheck;
import io.github.yashchenkon.dockercompose.junit5.annotation.WaitFor;
import io.github.yashchenkon.dockercompose.junit5.constant.HealthCheckType;
import it.ozimov.springboot.mail.service.TemplateService;
import it.ozimov.springboot.mail.service.exception.TemplateException;
import ua.social.network.notificationservice.NotificationServiceApplication;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Mykola Yashchenko
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = NotificationServiceApplication.class)
@DockerCompose(
        logs = "build/docker-compose",
        waitFor = @WaitFor(service = "sqs", healthCheck = @HealthCheck(type = HealthCheckType.TO_RESPOND_OVER_HTTP, port = 9324))
)
public class TemplateServiceTest {

    @Autowired
    private TemplateService templateService;

    @Test
    public void shouldProcessTemplate() throws IOException, TemplateException {
        final String result = templateService.mergeTemplateIntoString("welcome", Map.of("name", "test"));
        assertThat(result, Matchers.not(Matchers.isEmptyOrNullString()));
    }
}
