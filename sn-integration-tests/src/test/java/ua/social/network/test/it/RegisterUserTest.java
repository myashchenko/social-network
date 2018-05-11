package ua.social.network.test.it;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import ua.social.network.test.BaseTest;
import ua.social.network.test.api.RegisterUserRequest;

import static com.jayway.restassured.RestAssured.given;

/**
 * @author Mykola Yashchenko
 */
public class RegisterUserTest extends BaseTest {

    @Test
    public void shouldRegisterUser() {
        final String email = UUID.randomUUID().toString() + "@gmail.com";
        final RegisterUserRequest request = new RegisterUserRequest(
                email, UUID.randomUUID().toString(), UUID.randomUUID().toString()
        );

        given()
                .body(request)
                .post(baseHost() + "/sn-user-service/api/users")
                .then()
                .statusCode(200);
    }
}
