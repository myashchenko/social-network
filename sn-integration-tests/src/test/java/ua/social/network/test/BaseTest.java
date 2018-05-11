package ua.social.network.test;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.configuration.DockerComposeFiles;
import com.palantir.docker.compose.connection.Container;
import com.palantir.docker.compose.connection.waiting.HealthCheck;
import com.palantir.docker.compose.connection.waiting.HealthChecks;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import ua.social.network.test.api.Services;

import static org.joda.time.Duration.standardMinutes;

/**
 * @author Mykola Yashchenko
 */
public class BaseTest {

    private static final String LOG_DIRECTORY = "dockerLogs";

    private static final DockerComposeRule DOCKER_COMPOSE_RULE = DockerComposeRule.builder()
            .files(dockerCompose())
            .waitingForService(Services.GW_SERVICE, actuatorHealthCheck(8765), standardMinutes(4))
            .waitingForService(Services.AUTH_SERVICE, actuatorHealthCheck(9999), standardMinutes(4))
            .waitingForService(Services.USER_SERVICE, actuatorHealthCheck(8080), standardMinutes(4))
            .waitingForService(Services.USER_QUERY_SERVICE, actuatorHealthCheck(8080), standardMinutes(4))
            .saveLogsTo(LOG_DIRECTORY)
            .build();

    private static DockerComposeFiles dockerCompose() {
        return new DockerComposeFiles(Collections.singletonList(
                new File(BaseTest.class.getClassLoader().getResource("docker-compose.yml").getFile())));
    }

    private static HealthCheck<Container> actuatorHealthCheck(final Integer p) {
        return HealthChecks.toRespondOverHttp(p,
                port -> port.inFormat("http://$HOST:$EXTERNAL_PORT/actuator/health"));
    }

    public static String baseHost() {
        return DOCKER_COMPOSE_RULE.containers()
                .container(Services.GW_SERVICE)
                .port(8765)
                .inFormat("http://$HOST:$EXTERNAL_PORT");
    }

    @BeforeAll
    public static void beforeAll() throws IOException, InterruptedException {
        DOCKER_COMPOSE_RULE.before();
    }

    @AfterAll
    public static void afterAll() {
        DOCKER_COMPOSE_RULE.after();
    }
}
