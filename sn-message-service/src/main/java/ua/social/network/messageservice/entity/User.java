package ua.social.network.messageservice.entity;

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
@Table("users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @PrimaryKey
    private String id;
    private String name;
    private String avatarUrl;
}
