package ua.social.network.controller;

import com.sun.security.auth.UserPrincipal;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.social.UserQueryServiceApplication;
import ua.social.network.rule.JsonContentVerifier;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Mykola Yashchenko
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserQueryServiceApplication.class)
@Sql(scripts = "classpath:post/posts.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:truncate_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserPostControllerTest {

    @Autowired
    private UserPostController userPostController;

    @Autowired
    private ExceptionHandlerController exceptionHandlerController;

    private MockMvc mockMvc;

    @Rule
    public JsonContentVerifier jsonContentVerifier = new JsonContentVerifier(getClass());

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userPostController)
                .setControllerAdvice(exceptionHandlerController).build();
    }

    @Test
    public void testGetPost() throws Exception {
        String responseBody = mockMvc.perform(get("/users/posts/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        jsonContentVerifier.assertJson(responseBody);
    }

    @Test
    public void testGetPostWhichDoesNotExist() throws Exception {
        mockMvc.perform(get("/users/posts/1000").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetListByCurrentUser() throws Exception {
        String responseBody = mockMvc.perform(get("/users/posts").contentType(MediaType.APPLICATION_JSON)
                .principal(new UserPrincipal("USER-1@EMAIL.COM")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        jsonContentVerifier.assertJson(responseBody);
    }

    @Test
    public void testGetListByUserId() throws Exception {
        String responseBody = mockMvc.perform(get("/users/posts?user_id=2").contentType(MediaType.APPLICATION_JSON)
                .principal(new UserPrincipal("USER-1@EMAIL.COM")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        jsonContentVerifier.assertJson(responseBody);
    }
}
