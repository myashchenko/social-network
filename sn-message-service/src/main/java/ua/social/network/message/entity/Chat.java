package ua.social.network.message.entity;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Mykola Yashchenko
 */
@Getter
@Setter
@Builder
@Table("chats")
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    @PrimaryKey
    private String id = UUID.randomUUID().toString();
    private String title;
    private List<String> userIds;

    public Chat(final String title, final List<String> userIds) {
        this.title = title;
        this.userIds = userIds;
    }
}
