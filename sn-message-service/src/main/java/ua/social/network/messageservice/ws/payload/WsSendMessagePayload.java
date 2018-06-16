package ua.social.network.messageservice.ws.payload;

/**
 * @author Mykola Yashchenko
 */
public class WsSendMessagePayload implements WsPayload {
    private String toUserId;
    private String toChatId;
    private String text;
}
