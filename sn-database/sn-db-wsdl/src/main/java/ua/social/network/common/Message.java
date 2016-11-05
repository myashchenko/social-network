package ua.social.network.common;

import ua.social.network.services.Code;

/**
 * @author Mykola Yashchenko
 */
public enum Message {
    USER_ALREADY_EXISTS("User %s already exists", Code.ERROR),
    SYSTEM_ERROR("System error", Code.ERROR);

    private String message;
    private Code code;

    Message(String message, Code code) {
        this.message = message;
        this.code = code;
    }

    public Code getCode() {
        return code;
    }

    public String getMessage(String... args) {
        return String.format(message, (Object[]) args);
    }
}
