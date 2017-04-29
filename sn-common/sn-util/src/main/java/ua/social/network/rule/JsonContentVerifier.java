package ua.social.network.rule;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import junit.framework.AssertionFailedError;
import org.apache.commons.io.IOUtils;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Mykola Yashchenko
 */
public class JsonContentVerifier extends TestWatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonContentVerifier.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String DEFAULT_FILE_NAME = "response";
    private static final String PATH = "json/%s/%s/%s.json";

    private String methodName;

    private Class<?> testClass;

    @Override
    protected void starting(Description description) {
        this.testClass = description.getTestClass();
        this.methodName = description.getMethodName();
    }

    public void assertJson(String actualJson) throws IOException {
        assertJson(actualJson, DEFAULT_FILE_NAME);
    }

    public void assertJson(String actualJson, String expectedFileName) throws IOException {
        String path = String.format(PATH, testClass.getSimpleName(), methodName, expectedFileName);
        try (InputStream resourceAsStream = testClass.getClassLoader().getResourceAsStream(path)) {
            String expectedJson = IOUtils.toString(resourceAsStream);
            JsonNode actual = MAPPER.readTree(actualJson);
            JsonNode expected = MAPPER.readTree(expectedJson);

            LOGGER.info("EXPECTED JSON: " + expectedJson);
            LOGGER.info("ACTUAL JSON: " + actualJson);

            String diff = JsonDiff.asJson(actual, expected).toString();
            if (!diff.equals("[]")) {
                throw new AssertionFailedError("Json objects are not equal: " + diff);
            }
        }
    }
}
