package ua.social.network.controller;

import com.sun.security.auth.UserPrincipal;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.social.network.UserServiceApplication;
import ua.social.network.dto.CreatePostRequest;
import ua.social.network.dto.ModifyPostRequest;
import ua.social.network.entity.Post;
import ua.social.network.entity.User;
import ua.social.network.repository.UserPostRepository;
import ua.social.network.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Mykola Yashchenko
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceApplication.class)
public class UserPostControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private CommonExceptionHandlerController exceptionHandlerController;

    @InjectMocks
    private UserPostController userPostController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserPostRepository userPostRepository;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userPostController)
                .setControllerAdvice(exceptionHandlerController).build();
    }

    @Test
    public void shouldCreateNewPost() throws Exception {
        String receiverId = "RECEIVER_ID";
        String senderEmail = "SENDER@EMAIL.COM";

        Mockito.when(userRepository.findOne(Mockito.eq(receiverId))).thenReturn(new User());
        Mockito.when(userRepository.findByEmail(Mockito.eq(senderEmail))).thenReturn(new User());

        final CreatePostRequest post = new CreatePostRequest();
        post.setText("TEXT");
        post.setReceiverId(receiverId);

        String json = MAPPER.writeValueAsString(post);

        mockMvc.perform(post("/users/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .principal(new UserPrincipal(senderEmail)).content(json))
                .andExpect(status().isOk());

        Mockito.verify(userRepository).findOne(Mockito.eq(receiverId));
        Mockito.verify(userRepository).findByEmail(Mockito.eq(senderEmail));
        Mockito.verify(userPostRepository).save(Mockito.any(Post.class));
    }

    @Test
    public void shouldFailWhenTextIsEmpty() throws Exception {

        final CreatePostRequest post = new CreatePostRequest();
        post.setText("");
        post.setReceiverId("RECEIVER_ID");

        mockMvc.perform(post("/users/posts"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldFailWhenReceiverIsEmpty() throws Exception {

        final CreatePostRequest post = new CreatePostRequest();
        post.setText("TEXT");
        post.setReceiverId("");

        mockMvc.perform(post("/users/posts"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldFailWhenReceiverDoesNotExist() throws Exception {
        String receiverId = "RECEIVER_ID";
        String senderEmail = "SENDER@EMAIL.COM";

        final CreatePostRequest post = new CreatePostRequest();
        post.setText("TEXT");
        post.setReceiverId(receiverId);

        String json = MAPPER.writeValueAsString(post);

        mockMvc.perform(post("/users/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .principal(new UserPrincipal(senderEmail)).content(json))
                .andExpect(status().isNotFound());

        Mockito.verify(userRepository).findOne(Mockito.eq(receiverId));
    }

    @Test
    public void shouldModifyPost() throws Exception {
        String postId = "POST_ID";
        String senderEmail = "EMAIL@EMAIL.COM";

        Post postToModify = new Post();
        User sender = new User();
        sender.setEmail(senderEmail);
        postToModify.setSender(sender);
        Mockito.when(userPostRepository.findOne(Mockito.eq(postId))).thenReturn(postToModify);

        final ModifyPostRequest post = new ModifyPostRequest();
        post.setText("TEXT");

        String json = MAPPER.writeValueAsString(post);

        mockMvc.perform(put("/users/posts/" + postId)
                .principal(new UserPrincipal(senderEmail))
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());

        Mockito.verify(userPostRepository).findOne(Mockito.eq(postId));
        Mockito.verify(userPostRepository).save(Mockito.any(Post.class));
    }

    @Test
    public void shouldErrorWhenTextIsEmpty() throws Exception {
        String postId = "POST_ID";

        final ModifyPostRequest post = new ModifyPostRequest();
        post.setText("");

        String json = MAPPER.writeValueAsString(post);

        mockMvc.perform(put("/users/posts/" + postId)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldErrorWhenPostDoesNotExist() throws Exception {
        String postId = "POST_ID";

        Mockito.when(userPostRepository.findOne(Mockito.eq(postId))).thenReturn(null);

        final ModifyPostRequest post = new ModifyPostRequest();
        post.setText("TEXT");

        String json = MAPPER.writeValueAsString(post);

        mockMvc.perform(put("/users/posts/" + postId)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldErrorWhenPostSenderIsIncorrect() throws Exception {
        String postId = "POST_ID";

        Post postToModify = new Post();
        User sender = new User();
        sender.setEmail("EMAIL1");
        postToModify.setSender(sender);
        Mockito.when(userPostRepository.findOne(Mockito.eq(postId))).thenReturn(postToModify);

        final ModifyPostRequest post = new ModifyPostRequest();
        post.setText("TEXT");

        String json = MAPPER.writeValueAsString(post);

        mockMvc.perform(put("/users/posts/" + postId)
                .principal(new UserPrincipal("EMAIL2"))
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isForbidden());
    }
}