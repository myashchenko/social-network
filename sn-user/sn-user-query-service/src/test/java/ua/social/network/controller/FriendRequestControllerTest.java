package ua.social.network.controller;

import com.sun.security.auth.UserPrincipal;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import io.github.yashchenkon.test.JsonContentVerifier;
import ua.social.UserQueryServiceApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Mykola Yashchenko
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserQueryServiceApplication.class)
@Sql(scripts = "classpath:friend_request/friend_requests.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:truncate_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class FriendRequestControllerTest {

    @Autowired
    private FriendRequestController friendRequestController;

    @Autowired
    private CommonExceptionHandlerController exceptionHandlerController;

    @Rule
    public JsonContentVerifier jsonContentVerifier = new JsonContentVerifier();

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(friendRequestController)
                .setControllerAdvice(exceptionHandlerController).build();
    }

    @Test
    public void testGetList() throws Exception {
        String responseBody = mockMvc.perform(get("/friend_requests")
                .principal(new UserPrincipal("USER-4@EMAIL.COM")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        jsonContentVerifier.assertJson(responseBody);
    }
}
