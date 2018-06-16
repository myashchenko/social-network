package ua.social.network.userservice.controller;

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

import ua.social.network.oauth2.test.factory.TokenFactory;
import ua.social.network.userservice.UserServiceApplication;
import ua.social.network.userservice.dto.AssignRoleRequest;
import ua.social.network.userservice.entity.Role;
import ua.social.network.userservice.entity.User;
import ua.social.network.userservice.repository.UserRepository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Mykola Yashchenko
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceApplication.class)
@Sql(scripts = "classpath:user/users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:truncate_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RoleControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TokenFactory tokenFactory;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    public void shouldAssignRole() throws Exception {
        final AssignRoleRequest request = new AssignRoleRequest("ADMIN", "6");
        final String json = MAPPER.writeValueAsString(request);

        mockMvc.perform(put("/roles")
                .with(tokenFactory.token("5", "ui", "ROLE_SUPER_ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        final User user = userRepository.findById(request.getUserId()).get();
        assertThat(user.getRole(), equalTo(Role.ADMIN));
    }

    @Test
    public void shouldReturnErrorIfAssignerRoleIsNotSuperAdmin() throws Exception {
        final AssignRoleRequest request = new AssignRoleRequest("ADMIN", "6");
        final String json = MAPPER.writeValueAsString(request);

        mockMvc.perform(put("/roles")
                .with(tokenFactory.token("5", "ui", "ROLE_ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error.code").value("access_denied"))
                .andExpect(jsonPath("$.error.message").value("Access is denied"));

        final User user = userRepository.findById(request.getUserId()).get();
        assertThat(user.getRole(), equalTo(Role.USER));
    }

    @Test
    public void shouldReturnErrorIfRequestFieldIsEmpty() throws Exception {
        AssignRoleRequest request = new AssignRoleRequest("", "6");
        String json = MAPPER.writeValueAsString(request);

        mockMvc.perform(put("/roles")
                .with(tokenFactory.token("5", "ui", "ROLE_SUPER_ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value("SN-001"))
                .andExpect(jsonPath("$.error.message").value("Field 'role' may not be empty."));

        request = new AssignRoleRequest("ADMIN", "");
        json = MAPPER.writeValueAsString(request);

        mockMvc.perform(put("/roles")
                .with(tokenFactory.token("5", "ui", "ROLE_SUPER_ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value("SN-001"))
                .andExpect(jsonPath("$.error.message").value("Field 'userId' may not be empty."));
    }

    @Test
    public void shouldReturnErrorIfUserDoesNotExist() throws Exception {
        final AssignRoleRequest request = new AssignRoleRequest("ADMIN", UUID.randomUUID().toString());
        String json = MAPPER.writeValueAsString(request);

        mockMvc.perform(put("/roles")
                .with(tokenFactory.token("5", "ui", "ROLE_SUPER_ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.code").value("US-001"))
                .andExpect(jsonPath("$.error.message").value("User not found."));
    }

}
