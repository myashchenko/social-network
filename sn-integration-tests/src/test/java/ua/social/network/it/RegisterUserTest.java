package ua.social.network.it;

import org.junit.jupiter.api.Test;

import ua.social.network.BaseTest;

import static com.jayway.restassured.RestAssured.when;

/**
 * @author Mykola Yashchenko
 */
public class RegisterUserTest extends BaseTest {

    @Test
    public void shouldRegisterUser() {
        when()
                .post(baseHost() + "/sn-user-service/api/users")
                .then()
                .statusCode(200);
    }
}
