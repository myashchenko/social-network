package ua.social.network.message.ws;

import ua.social.network.message.ws.payload.WsPayload;

/**
 * @author Mykola Yashchenko
 */
public class WsEvent {
    public enum WsEventType {
        CHAT_MESSAGE,
        USER_ONLINE,
        USER_OFFLINE,
        USER_READ_MESSAGE,
        USER_IS_TYPING
    }

    private WsEventType type;
    private WsPayload payload;
}
