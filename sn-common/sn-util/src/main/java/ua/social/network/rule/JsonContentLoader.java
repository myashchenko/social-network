package ua.social.network.rule;

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
public class JsonContentLoader extends TestWatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonContentLoader.class);

    private static final String DEFAULT_FILE_NAME = "request";
    private static final String PATH = "json/%s/%s/%s.json";

    private String methodName;

    private Class<?> testClass;

    @Override
    protected void starting(Description description) {
        this.testClass = description.getTestClass();
        this.methodName = description.getMethodName();
    }

    public String load() throws IOException {
        return load(DEFAULT_FILE_NAME);
    }

    public String load(String fileName) throws IOException {
        String path = String.format(PATH, testClass.getSimpleName(), methodName, fileName);
        LOGGER.info("Reading file: {}", path);
        try (InputStream resourceAsStream = testClass.getClassLoader().getResourceAsStream(path)) {
            String json = IOUtils.toString(resourceAsStream);
            LOGGER.info("Loaded JSON: \n{}", json);
            return json;
        }
    }
}
