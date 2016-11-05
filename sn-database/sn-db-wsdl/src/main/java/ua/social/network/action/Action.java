package ua.social.network.action;

/**
 * @author Mykola Yashchenko
 */
@FunctionalInterface
public interface Action<RES> {
    RES execute();
}
