package ua.social.network.query;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertThat;

/**
 * @author Mykola Yashchenko
 */
public class ExpandParamsBuilderTest {

    @Test(expected = NullPointerException.class)
    public void testNull() {
        new ExpandParamsBuilder(null);
    }

    @Test
    public void testEmpty() {
        Map<String, Object> params = new ExpandParamsBuilder("").build();
        assertThat(params.size(), Matchers.is(0));
    }

    @Test
    public void testOneParam() {
        Map<String, Object> params = new ExpandParamsBuilder("param").build();
        assertThat(params.size(), Matchers.is(1));
        assertThat(params.containsKey("expandParam"), Matchers.is(true));
        assertThat(params.get("expandParam"), Matchers.is(true));
    }

    @Test
    public void testManyParams() {
        String params = "param1, param2, param3";
        Map<String, Object> paramsMap = new ExpandParamsBuilder(params).build();
        assertThat(paramsMap.size(), Matchers.is(3));
        for (String param : params.split(",")) {
            param = param.trim();
            String key = "expand" + Character.toUpperCase(param.charAt(0)) + param.substring(1).toLowerCase();
            assertThat(paramsMap.containsKey(key), Matchers.is(true));
            assertThat(paramsMap.get(key), Matchers.is(true));
        }
    }
}
