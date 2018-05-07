package ua.social.network.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mykola Yashchenko
 */
@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final Error error;
}
