package ua.social.network.controller;

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
import ua.social.network.CommunityQueryServiceApplication;
import ua.social.network.rule.JsonContentVerifier;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Mykola Yashchenko
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommunityQueryServiceApplication.class)
@Sql(scripts = "classpath:community/communities.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:truncate_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CommunityControllerTest {

    @Autowired
    private CommunityController communityController;

    @Autowired
    private ExceptionHandlerController exceptionHandlerController;

    private MockMvc mockMvc;

    @Rule
    public JsonContentVerifier jsonContentVerifier = new JsonContentVerifier(this.getClass());

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(communityController)
                .setControllerAdvice(exceptionHandlerController).build();
    }

    @Test
    public void testGetCommunity() throws Exception {
        String responseBody = mockMvc.perform(get("/communities/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        jsonContentVerifier.assertJson(responseBody);
    }

    @Test
    public void testGetCommunityWhichDoesNotExist() throws Exception {
        mockMvc.perform(get("/communities/1000").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
