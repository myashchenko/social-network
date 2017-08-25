package ua.social.network.controller;

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
@Sql(scripts = "classpath:user/users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:truncate_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerTest {

    @Rule
    public JsonContentVerifier jsonContentVerifier = new JsonContentVerifier();
    @Autowired
    private UserController userController;
    @Autowired
    private CommonExceptionHandlerController exceptionHandlerController;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(exceptionHandlerController).build();
    }

    @Test
    public void testGetUserWithoutFriendsWithoutExpandParam() throws Exception {
        String responseBody = mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        jsonContentVerifier.assertJson(responseBody);
    }

    @Test
    public void testGetUserWithoutFriendsWithExpandParam() throws Exception {
        String responseBody = mockMvc.perform(get("/users/1?expand=friends"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        jsonContentVerifier.assertJson(responseBody);
    }

    @Test
    public void testGetUserWithFriendsWithoutExpandParam() throws Exception {
        String responseBody = mockMvc.perform(get("/users/2"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        jsonContentVerifier.assertJson(responseBody);
    }

    @Test
    public void testGetUserWithFriendsWithExpandParam() throws Exception {
        String responseBody = mockMvc.perform(get("/users/2?expand=friends"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        jsonContentVerifier.assertJson(responseBody);
    }

    @Test
    public void testGetUserWhichDoesNotExist() throws Exception {
        mockMvc.perform(get("/users/1000"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetList() throws Exception {
        String responseBody = mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        jsonContentVerifier.assertJson(responseBody);
    }

    @Test
    public void testGetFriends() throws Exception {
        String responseBody = mockMvc.perform(get("/users?user_id=2"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        jsonContentVerifier.assertJson(responseBody);
    }

    @Test
    public void testGetUserWithFriendRequests() throws Exception {
        String responseBody = mockMvc.perform(get("/users/5"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        jsonContentVerifier.assertJson(responseBody);
    }
}
