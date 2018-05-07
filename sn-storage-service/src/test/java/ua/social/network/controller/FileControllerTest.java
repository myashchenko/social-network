package ua.social.network.controller;

import java.util.UUID;

import org.apache.http.entity.ContentType;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ua.social.network.StorageServiceApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Mykola Yashchenko
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StorageServiceApplication.class)
public class FileControllerTest {

    @Autowired
    protected WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldUploadFile() throws Exception {
        final String fileName = "file.txt";
        final String fileContent = "123";

        final MockMultipartFile fileToUpload = new MockMultipartFile(
                "file", fileName, ContentType.TEXT_PLAIN.getMimeType(), fileContent.getBytes());

        final Matcher<String> urlMatcher = Matchers.allOf(
                Matchers.startsWith("http://localhost:8080/api/files/"),
                Matchers.endsWith("_" + fileName)
        );
        mockMvc.perform(multipart("/api/files")
                .file(fileToUpload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value(urlMatcher));
    }

    @Test
    public void shouldReturnErrorIfFileIsEmpty() throws Exception {
        final String fileName = "file.txt";
        final String fileContent = "";

        final MockMultipartFile fileToUpload = new MockMultipartFile(
                "file", fileName, ContentType.TEXT_PLAIN.getMimeType(), fileContent.getBytes());

        mockMvc.perform(multipart("/api/files")
                .file(fileToUpload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.message").value("Storage exception."))
                .andExpect(jsonPath("$.error.code").value("SS-002"));
    }

    @Test
    public void shouldRetrieveFile() throws Exception {
        final String fileName = "file.txt";
        final String fileContent = "123";

        final MockMultipartFile fileToUpload = new MockMultipartFile(
                "file", fileName, ContentType.TEXT_PLAIN.getMimeType(), fileContent.getBytes());

        final Matcher<String> urlMatcher = Matchers.allOf(
                Matchers.startsWith("http://localhost:8080/api/files/"),
                Matchers.endsWith("_" + fileName),
                new BaseMatcher<>() {
                    @Override
                    public void describeTo(final Description description) {
                        description.appendText("File");
                    }

                    @Override
                    public boolean matches(final Object item) {
                        final String url = (String) item;
                        try {
                            final byte[] fileContentBytes = mockMvc.perform(get("/api/files/" + url.replaceAll("http://localhost:8080/api/files/", "")))
                                    .andExpect(status().isOk())
                                    .andReturn().getResponse().getContentAsByteArray();
                            return fileContent.equals(new String(fileContentBytes));
                        } catch (final Exception e) {
                            return false;
                        }
                    }
                }
        );

        mockMvc.perform(multipart("/api/files")
                .file(fileToUpload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value(urlMatcher));
    }

    @Test
    public void shouldReturn404IfFileDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/files/" + UUID.randomUUID().toString()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.message").value("File not found."))
                .andExpect(jsonPath("$.error.code").value("SS-001"));
    }
}
