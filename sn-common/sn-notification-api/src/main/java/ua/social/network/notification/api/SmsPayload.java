package ua.social.network.notification.api;

import lombok.Builder;

/**
 * @author Mykola Yashchenko
 */
@Builder
public class SmsPayload implements Payload {
    public final String phone;
    public final String text;
}
