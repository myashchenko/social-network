package ua.social.network.matcher;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.util.Objects;

/**
 * @author Mykola Yashchenko
 */
public class EntityMatcher<T> extends BaseMatcher<T> {

    private T expected;

    public EntityMatcher(T expected) {
        this.expected = expected;
    }

    @Override
    public boolean matches(Object actual) {
        return Objects.equals(expected, actual);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(expected != null ? expected.toString() : null);
    }
}
