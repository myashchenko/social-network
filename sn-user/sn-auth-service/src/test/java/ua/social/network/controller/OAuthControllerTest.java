package ua.social.network.controller;

import java.util.Base64;
import java.util.Map;
import java.util.UUID;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import io.jsonwebtoken.Jwts;
import ua.social.network.AuthApplication;
import ua.social.network.entity.Role;
import ua.social.network.entity.User;
import ua.social.network.repository.UserRepository;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Mykola Yashchenko
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthApplication.class)
public class OAuthControllerTest {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }

    @Test
    public void shouldAuthorizeMicroservice() throws Exception {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "client_credentials");
        params.add("client_id", "sn-user-service");

        mockMvc.perform(post("/oauth/token")
                .params(params)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + new String(Base64.getEncoder().encode("sn-user-service:123".getBytes())))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldAuthorizeUser() throws Exception {
        final String username = UUID.randomUUID().toString() + "@domain.com";
        final String password = "12345678";

        final User user = new User();
        user.setEmail(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(UUID.randomUUID().toString());
        user.setRole(Role.USER);

        userRepository.save(user);

        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", username);
        params.add("password", password);
        params.add("scope", "ui");

        mockMvc.perform(post("/oauth/token")
                .params(params)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + new String(Base64.getEncoder().encode("browser:secret".getBytes())))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.access_token").isNotEmpty())
                .andExpect(jsonPath("$.access_token").value(new BaseMatcher<String>() {

                    @Override
                    public boolean matches(final Object item) {
                        String jwt = (String) item;
                        jwt = jwt.substring(0, jwt.lastIndexOf(".") + 1);
                        final Map<String, Object> claims = (Map<String, Object>) Jwts.parser().parse(jwt).getBody();
                        return claims.get("user_id").equals(user.getId());
                    }

                    @Override
                    public void describeTo(final Description description) {
                        description.appendText("user_id doesn't match");
                    }
                }));

    }
}
