package ua.social.network.exception;

import ua.social.network.common.Message;

/**
 * @author Mykola Yashchenko
 */
public class ServiceException extends RuntimeException {
    private Message message;
    private String[] args;

    public ServiceException(Message message, String... args) {
        this.message = message;
        this.args = args;
    }

    public Message getStatusMessage() {
        return message;
    }

    public String[] getStatusArgs() {
        return args;
    }
}
