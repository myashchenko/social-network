package ua.social.network.gateway;

import com.github.tomakehurst.wiremock.WireMockServer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

/**
 * @author Mykola Yashchenko
 */
@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = GatewayApplication.class)
public class GatewayApplicationTest {

    private static WireMockServer wireMockServer = new WireMockServer(8080);

    @Autowired
    private WebTestClient client;

    @BeforeAll
    public static void before() {
        wireMockServer.start();
    }

    @AfterAll
    public static void after() {
        wireMockServer.stop();
    }

    @Test
    public void shouldLoadContext() {

    }

    @Test
    @Disabled
    public void shouldRouteRequests() {
        stubFor(get(urlEqualTo("/test"))
                .willReturn(okJson("{}")));

        client.get().uri("/storage-service/api/test")
                .exchange()
                .expectStatus().isOk();
    }
}
