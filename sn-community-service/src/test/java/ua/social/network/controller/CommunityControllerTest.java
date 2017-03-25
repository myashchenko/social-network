package ua.social.network.controller;

import com.sun.security.auth.UserPrincipal;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.social.network.CommunityServiceApplication;
import ua.social.network.dto.CreateCommunityRequest;
import ua.social.network.dto.ModifyCommunityRequest;
import ua.social.network.entity.Community;
import ua.social.network.repository.CommunityRepository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Mykola Yashchenko
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CommunityServiceApplication.class)
public class CommunityControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private ExceptionHandlerController exceptionHandlerController;

    @InjectMocks
    private CommunityController communityController;

    @Mock
    private CommunityRepository communityRepository;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(communityController)
                .setControllerAdvice(exceptionHandlerController).build();
    }

    @Test
    public void shouldCreateCommunity() throws Exception {

        String communityName = "testCommunity";

        CreateCommunityRequest createCommunityRequest = new CreateCommunityRequest();
        createCommunityRequest.setName(communityName);

        mockMvc.perform(post("/").principal(new UserPrincipal("testUser"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createCommunityRequest)))
                .andExpect(status().isOk());

        ArgumentCaptor<Community> argumentCaptor = ArgumentCaptor.forClass(Community.class);
        verify(communityRepository, times(1)).save(argumentCaptor.capture());

        Community community = argumentCaptor.getValue();
        assertThat(community.getName(), equalTo(createCommunityRequest.getName()));
        assertThat(community.getUserId(), equalTo("testUser"));
    }

    @Test
    public void shouldNotCreateCommunityWithBlankName() throws Exception {

        String communityName = "";

        CreateCommunityRequest createCommunityRequest = new CreateCommunityRequest();
        createCommunityRequest.setName(communityName);

        mockMvc.perform(post("/").principal(new UserPrincipal("testUser"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createCommunityRequest)))
                .andExpect(status().isBadRequest());

        verify(communityRepository, times(0)).save(any(Community.class));
    }

    @Test
    public void shouldModifyCommunity() throws Exception {
        String id = "123";

        Community community = new Community();
        community.setId(id);
        community.setName("Some name");
        community.setUserId("testUser");

        when(communityRepository.findOne(eq(id))).thenReturn(community);

        String communityName = "testCommunity";

        ModifyCommunityRequest modifyCommunityRequest = new ModifyCommunityRequest();
        modifyCommunityRequest.setName(communityName);

        mockMvc.perform(put("/" + id).principal(new UserPrincipal("testUser"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(modifyCommunityRequest)))
                .andExpect(status().isOk());

        ArgumentCaptor<Community> argumentCaptor = ArgumentCaptor.forClass(Community.class);
        verify(communityRepository).save(argumentCaptor.capture());

        community = argumentCaptor.getValue();
        assertThat(community.getName(), equalTo(modifyCommunityRequest.getName()));
        assertThat(community.getUserId(), equalTo("testUser"));
    }

    @Test
    public void shouldNotModifyCommunityWithBlankName() throws Exception {
        String id = "123";

        String communityName = "";

        ModifyCommunityRequest modifyCommunityRequest = new ModifyCommunityRequest();
        modifyCommunityRequest.setName(communityName);

        mockMvc.perform(put("/" + id).principal(new UserPrincipal("testUser"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(modifyCommunityRequest)))
                .andExpect(status().isBadRequest());

        verify(communityRepository, times(0)).findOne(eq(id));
    }

    @Test
    public void shouldNotModifyCommunityOfOtherUser() throws Exception {
        String id = "123";

        Community community = new Community();
        community.setId(id);
        community.setName("Some name");
        community.setUserId("testUser");

        when(communityRepository.findOne(eq(id))).thenReturn(community);

        String communityName = "testName";

        ModifyCommunityRequest modifyCommunityRequest = new ModifyCommunityRequest();
        modifyCommunityRequest.setName(communityName);

        mockMvc.perform(put("/" + id).principal(new UserPrincipal("notAdmin"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(modifyCommunityRequest)))
                .andExpect(status().isBadRequest());

        verify(communityRepository, times(1)).findOne(eq(id));
    }


    @Test
    public void shouldReturnErrorIfCommunityDoesNotExist() throws Exception {
        String id = "123";

        when(communityRepository.findOne(eq(id))).thenReturn(null);

        String communityName = "testName";

        ModifyCommunityRequest modifyCommunityRequest = new ModifyCommunityRequest();
        modifyCommunityRequest.setName(communityName);

        mockMvc.perform(put("/" + id).principal(new UserPrincipal("notAdmin"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(modifyCommunityRequest)))
                .andExpect(status().isBadRequest());

        verify(communityRepository, times(1)).findOne(eq(id));
    }
}
