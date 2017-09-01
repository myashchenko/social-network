package ua.social.network.controller;

import java.io.IOException;
import java.nio.file.Path;

import com.sun.security.auth.UserPrincipal;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ua.social.network.UserServiceApplication;
import ua.social.network.dto.CreateUserRequest;
import ua.social.network.entity.File;
import ua.social.network.entity.Role;
import ua.social.network.entity.User;
import ua.social.network.repository.FileRepository;
import ua.social.network.repository.UserRepository;
import ua.social.network.service.StorageService;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Mykola Yashchenko
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceApplication.class)
@Sql(scripts = "classpath:user/users.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:truncate_tables.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private UserController accountController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private CommonExceptionHandlerController commonExceptionHandler;

    @Autowired
    private StorageService storageService;

    @ClassRule
    public static TemporaryFolder temporaryFolder = new TemporaryFolder();

    private static Path filesPath;

    private MockMvc mockMvc;

    @BeforeClass
    public static void prepareFolder() throws IOException {
        filesPath = temporaryFolder.newFolder().toPath();
    }

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController)
                .setControllerAdvice(commonExceptionHandler).build();

        ReflectionTestUtils.setField(storageService, "filesRootPath", filesPath);
    }

    @Test
    public void shouldCreateNewUser() throws Exception {

        CreateUserRequest user = new CreateUserRequest();
        user.setEmail("test@test.com");
        user.setPassword("password");
        user.setName("Name Name");

        String json = MAPPER.writeValueAsString(user);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        User actualUser = userRepository.findByEmail(user.getEmail());
        assertThat(actualUser, notNullValue());
        assertThat(actualUser.getEmail(), equalTo(user.getEmail()));
        assertThat(actualUser.getName(), equalTo(user.getName()));
        assertThat(actualUser.getPassword(), notNullValue());
        assertThat(actualUser.getRole(), equalTo(Role.USER));
    }

    @Test
    public void shouldFailWhenUserIsNotValid() throws Exception {

        final CreateUserRequest user = new CreateUserRequest();
        user.setEmail("t");
        user.setPassword("p");
        user.setName("n");

        String json = MAPPER.writeValueAsString(user);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUploadAvatar() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                "text/plain", "Fake".getBytes());

        mockMvc.perform(fileUpload("/users/1/avatar")
                .file(multipartFile)
                .principal(new UserPrincipal("USER-1")))
                .andExpect(status().isOk());

        File actualFile = fileRepository.findByFilePath(filesPath.toString() + "/test.txt");
        assertThat(actualFile, notNullValue());
        assertThat(actualFile.getUser(), equalTo("USER-1"));
    }
}