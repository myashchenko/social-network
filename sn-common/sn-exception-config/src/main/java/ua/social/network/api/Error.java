package ua.social.network.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mykola Yashchenko
 */
@Getter
@AllArgsConstructor
public class Error {
    private final String message;
    private final String code;
}
