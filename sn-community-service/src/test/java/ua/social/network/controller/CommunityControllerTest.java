package ua.social.network.controller;

import com.sun.security.auth.UserPrincipal;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.social.network.CommunityServiceApplication;
import ua.social.network.dto.CreateCommunityRequest;
import ua.social.network.entity.Community;
import ua.social.network.repository.CommunityRepository;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Mykola Yashchenko
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CommunityServiceApplication.class)
public class CommunityControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    private CommunityController communityController;

    @Mock
    private CommunityRepository communityRepository;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(communityController).build();
    }

    @Test
    public void shouldCreateCommunity() throws Exception {
        String communityName = "testCommunity";

        CreateCommunityRequest createCommunityRequest = new CreateCommunityRequest();
        createCommunityRequest.setName(communityName);

        mockMvc.perform(post("/communities").principal(new UserPrincipal("testUser"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createCommunityRequest)))
                .andExpect(status().isOk());

//        Community community = communityRepository.findByName(communityName);
//        assertThat(community, notNullValue());
//        assertThat(community.getUserId(), equalTo("testUser"));
    }
}
