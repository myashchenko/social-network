package ua.social.network.notificationservice.api;

import lombok.Builder;

/**
 * @author Mykola Yashchenko
 */
@Builder
public class SmsPayload implements Payload {
    public final String phone;
    public final String text;
}
