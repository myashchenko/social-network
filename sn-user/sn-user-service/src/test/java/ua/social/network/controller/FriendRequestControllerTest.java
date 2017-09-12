package ua.social.network.controller;

import java.util.Optional;

import com.sun.security.auth.UserPrincipal;

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

import ua.social.network.UserServiceApplication;
import ua.social.network.dto.AddFriendRequest;
import ua.social.network.entity.FriendRequest;
import ua.social.network.entity.User;
import ua.social.network.repository.FriendRequestRepository;
import ua.social.network.repository.UserRepository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
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
    private FriendRequestController friendRequestController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private CommonExceptionHandlerController commonExceptionHandler;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(friendRequestController)
                .setControllerAdvice(commonExceptionHandler).build();
    }

    @Test
    @Transactional
    public void shouldSendFriendRequest() throws Exception {
        String friendUUID = "2";

        AddFriendRequest addFriendRequest = new AddFriendRequest();
        addFriendRequest.setUserId(friendUUID);

        String json = MAPPER.writeValueAsString(addFriendRequest);

        mockMvc.perform(post("/friend_requests")
                .contentType(MediaType.APPLICATION_JSON)
                .principal(new UserPrincipal("USER-1@EMAIL.COM"))
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
                .principal(new UserPrincipal("USER-1@EMAIL.COM"))
                .content(json))
                .andExpect(status().isBadRequest());
    }


    @Test
    @Ignore("Ignore since there is no handler for DataIntegrityViolationException")
    public void shouldReturnErrorIfRequestHasAlreadySent() throws Exception {
        AddFriendRequest addFriendRequest = new AddFriendRequest();
        addFriendRequest.setUserId("4");

        String json = MAPPER.writeValueAsString(addFriendRequest);

        mockMvc.perform(post("/friend_requests")
                .contentType(MediaType.APPLICATION_JSON)
                .principal(new UserPrincipal("USER-3@EMAIL.COM"))
                .content(json))
                .andExpect(status().isForbidden());

        addFriendRequest = new AddFriendRequest();
        addFriendRequest.setUserId("3");

        json = MAPPER.writeValueAsString(addFriendRequest);

        mockMvc.perform(post("/friend_requests")
                .contentType(MediaType.APPLICATION_JSON)
                .principal(new UserPrincipal("USER-4@EMAIL.COM"))
                .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnErrorIfUsersAreFriendsAlready() throws Exception {
        AddFriendRequest addFriendRequest = new AddFriendRequest();
        addFriendRequest.setUserId("4");

        String json = MAPPER.writeValueAsString(addFriendRequest);

        mockMvc.perform(post("/friend_requests")
                .contentType(MediaType.APPLICATION_JSON)
                .principal(new UserPrincipal("USER-2@EMAIL.COM"))
                .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void shouldAcceptFriendRequest() throws Exception {

        mockMvc.perform(post("/friend_requests/2")
                .contentType(MediaType.APPLICATION_JSON)
                .principal(new UserPrincipal("USER-4@EMAIL.COM")))
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
                .contentType(MediaType.APPLICATION_JSON)
                .principal(new UserPrincipal("USER-4@EMAIL.COM")))
                .andExpect(status().isNotFound());
    }
}