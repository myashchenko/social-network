package ua.social.network.controller;

import java.util.UUID;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ua.social.network.UserServiceApplication;
import ua.social.network.dto.CreatePostRequest;
import ua.social.network.dto.ModifyPostRequest;
import ua.social.network.oauth2.test.factory.TokenFactory;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Mykola Yashchenko
 */
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:user/users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:truncate_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(classes = UserServiceApplication.class)
public class UserPostControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TokenFactory tokenFactory;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    public void shouldCreateNewPost() throws Exception {
        final String receiverId = "2";

        final CreatePostRequest post = new CreatePostRequest();
        post.setText("TEXT");
        post.setReceiverId(receiverId);

        final String json = MAPPER.writeValueAsString(post);

        mockMvc.perform(post("/users/posts")
                .with(tokenFactory.token("1", "ui"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFailWhenTextIsEmpty() throws Exception {

        final CreatePostRequest post = new CreatePostRequest();
        post.setText("");
        post.setReceiverId("RECEIVER_ID");

        mockMvc.perform(post("/users/posts")
                .with(tokenFactory.token("1", "ui")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldFailWhenReceiverIsEmpty() throws Exception {

        final CreatePostRequest post = new CreatePostRequest();
        post.setText("TEXT");
        post.setReceiverId("");

        mockMvc.perform(post("/users/posts")
                .with(tokenFactory.token("1", "ui")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldFailWhenReceiverDoesNotExist() throws Exception {
        final String receiverId = UUID.randomUUID().toString();

        final CreatePostRequest post = new CreatePostRequest();
        post.setText("TEXT");
        post.setReceiverId(receiverId);

        final String json = MAPPER.writeValueAsString(post);

        mockMvc.perform(post("/users/posts")
                .with(tokenFactory.token("1", "ui"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldModifyPost() throws Exception {
        final String postId = "1";

        final ModifyPostRequest post = new ModifyPostRequest();
        post.setText("TEXT");

        String json = MAPPER.writeValueAsString(post);

        mockMvc.perform(put("/users/posts/" + postId)
                .with(tokenFactory.token("1", "ui"))
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldErrorWhenTextIsEmpty() throws Exception {
        final String postId = "POST_ID";

        final ModifyPostRequest post = new ModifyPostRequest();
        post.setText("");

        final String json = MAPPER.writeValueAsString(post);

        mockMvc.perform(put("/users/posts/" + postId)
                .with(tokenFactory.token("1", "ui"))
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldErrorWhenPostDoesNotExist() throws Exception {
        final String postId = UUID.randomUUID().toString();

        final ModifyPostRequest post = new ModifyPostRequest();
        post.setText("TEXT");

        final String json = MAPPER.writeValueAsString(post);

        mockMvc.perform(put("/users/posts/" + postId)
                .with(tokenFactory.token("1", "ui"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldErrorWhenPostSenderIsIncorrect() throws Exception {
        final String postId = "1";

        final ModifyPostRequest post = new ModifyPostRequest();
        post.setText("TEXT");

        final String json = MAPPER.writeValueAsString(post);

        mockMvc.perform(put("/users/posts/" + postId)
                .with(tokenFactory.token("2", "ui"))
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNotFound());
    }
}