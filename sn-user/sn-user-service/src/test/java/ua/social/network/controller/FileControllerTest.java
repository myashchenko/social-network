package ua.social.network.controller;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ua.social.network.UserServiceApplication;
import ua.social.network.service.StorageService;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Mykola Yashchenko
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceApplication.class)
public class FileControllerTest {

    @Autowired
    private FileController fileController;

    @Autowired
    private UserController userController;

    @Autowired
    private CommonExceptionHandlerController commonExceptionHandler;

    @MockBean
    private StorageService storageService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(fileController, userController)
                .setControllerAdvice(commonExceptionHandler).build();
    }

    @Test
    public void shouldReturnFile() throws Exception {
        URL resource = getClass().getClassLoader().getResource("user/users.sql");
        when(storageService.load(eq("file.txt"))).thenReturn(new FileSystemResource(resource.getPath()));

        String result = mockMvc.perform(get("/files/file.txt"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(result, notNullValue());
    }
}
