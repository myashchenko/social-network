package ua.social.network.controller;

import java.util.Optional;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import ua.social.network.UserServiceApplication;
import ua.social.network.dto.AddFriendRequest;
import ua.social.network.entity.FriendRequest;
import ua.social.network.entity.User;
import ua.social.network.oauth2.test.factory.TokenFactory;
import ua.social.network.repository.FriendRequestRepository;
import ua.social.network.repository.UserRepository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Mykola Yashchenko
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceApplication.class)
@Sql(scripts = "classpath:user/users.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:truncate_tables.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class FriendRequestControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private TokenFactory tokenFactory;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }

    @Test
    public void shouldSendFriendRequest() throws Exception {
        String friendUUID = "2";

        AddFriendRequest addFriendRequest = new AddFriendRequest();
        addFriendRequest.setUserId(friendUUID);

        String json = MAPPER.writeValueAsString(addFriendRequest);

        mockMvc.perform(post("/friend_requests")
                .with(tokenFactory.token("1", "ui"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        User from = userRepository.getOne("1");
        User to = userRepository.getOne(friendUUID);

        FriendRequest req = friendRequestRepository.findRequestByUsers(from, to);
        assertThat(req, notNullValue());
        assertThat(req.getFrom().getId(), equalTo(from.getId()));
        assertThat(req.getTo().getId(), equalTo(to.getId()));
    }

    @Test
    public void shouldNotSendIncorrectFriendRequest() throws Exception {
        AddFriendRequest addFriendRequest = new AddFriendRequest();
        addFriendRequest.setUserId("");

        String json = MAPPER.writeValueAsString(addFriendRequest);

        mockMvc.perform(post("/friend_requests")
                .contentType(MediaType.APPLICATION_JSON)
                .with(tokenFactory.token("1", "ui"))
                .content(json))
                .andExpect(status().isBadRequest());
    }


    @Test
    @Ignore("todo")
    public void shouldReturnErrorIfRequestHasAlreadySent() throws Exception {
        AddFriendRequest addFriendRequest = new AddFriendRequest();
        addFriendRequest.setUserId("4");

        String json = MAPPER.writeValueAsString(addFriendRequest);

        mockMvc.perform(post("/friend_requests")
                .with(tokenFactory.token("3", "ui"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isForbidden());

        addFriendRequest = new AddFriendRequest();
        addFriendRequest.setUserId("3");

        json = MAPPER.writeValueAsString(addFriendRequest);

        mockMvc.perform(post("/friend_requests")
                .with(tokenFactory.token("4", "ui"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnErrorIfUsersAreFriendsAlready() throws Exception {
        AddFriendRequest addFriendRequest = new AddFriendRequest();
        addFriendRequest.setUserId("4");

        String json = MAPPER.writeValueAsString(addFriendRequest);

        mockMvc.perform(post("/friend_requests")
                .with(tokenFactory.token("2", "ui"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void shouldAcceptFriendRequest() throws Exception {

        mockMvc.perform(post("/friend_requests/2")
                .with(tokenFactory.token("4", "ui"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Optional<FriendRequest> friendRequest = friendRequestRepository.findById("2");
        assertThat(friendRequest.isPresent(), is(false));

        User user1 = userRepository.findById("4").get();
        User user2 = userRepository.findById("1").get();

        assertThat(user1.friendOf(user2), is(true));
        assertThat(user2.friendOf(user1), is(true));
    }

    @Test
    public void shouldReturnErrorIfFriendRequestDoesNotExist() throws Exception {

        mockMvc.perform(post("/friend_requests/DOES_NOT_EXIST")
                .with(tokenFactory.token("4", "ui"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}