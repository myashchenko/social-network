package ua.social.network.extension;

import java.io.File;
import java.util.Collections;

import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.configuration.DockerComposeFiles;
import com.palantir.docker.compose.connection.waiting.HealthChecks;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * @author Mykola Yashchenko
 */
public class DockerComposeExtension implements BeforeAllCallback, AfterAllCallback {

    private DockerComposeRule dockerComposeRule = DockerComposeRule.builder()
            .files(new DockerComposeFiles(Collections.singletonList(new File(getClass().getClassLoader().getResource("docker-compose.yml").getFile()))))
            .waitingForService("sn-gateway-service", HealthChecks.toHaveAllPortsOpen())
            .build();

    @Override
    public void beforeAll(final ExtensionContext context) throws Exception {
        dockerComposeRule.before();
    }

    @Override
    public void afterAll(final ExtensionContext context) {
        dockerComposeRule.after();
    }
}
