package ua.social.network.notification.api;

import java.util.Map;

import lombok.Builder;

/**
 * @author Mykola Yashchenko
 */
@Builder
public class EmailPayload implements Payload {
    public final String email;
    public final String subject;
    public final EmailTemplate template;
    public final Map<String, Object> templateModel;
}
