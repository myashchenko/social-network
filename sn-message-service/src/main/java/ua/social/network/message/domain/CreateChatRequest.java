package ua.social.network.message.domain;

import java.util.List;

import lombok.Data;

/**
 * @author Mykola Yashchenko
 */
@Data
public class CreateChatRequest {
    private String title;
    private List<String> userIds;
}
