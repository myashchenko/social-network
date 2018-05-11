package ua.social.network.test.environment;

import java.util.Objects;

/**
 * @author Mykola Yashchenko
 */
public final class AWSEnvironment implements Environment {

    private final String baseHost;

    public AWSEnvironment(final String baseHost) {
        this.baseHost = Objects.requireNonNull(baseHost);
    }

    @Override
    public String baseHost() {
        return baseHost;
    }

    @Override
    public void beforeTests() {

    }

    @Override
    public void afterTests() {

    }
}
