package ua.social.network.test.environment;

import ua.social.network.test.api.Properties;

/**
 * @author Mykola Yashchenko
 */
public class EnvironmentFactory {

    public Environment build() {
        final String envType = System.getProperty(Properties.ENV_TYPE);
        if (envType == null) {
            throw new RuntimeException("Environment type is incorrect.");
        }

        switch (envType) {
            case Properties.ENV_TYPE_AWS:
                return new AWSEnvironment(System.getProperty(Properties.CLUSTER_HOST));
            case Properties.ENV_TYPE_DOCKER:
                return new DockerEnvironment();
            default:
                throw new RuntimeException("Environment type is incorrect.");
        }
    }
}
