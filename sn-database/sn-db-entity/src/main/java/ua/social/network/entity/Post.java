package ua.social.network.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Mykola Yashchenko
 */
@Entity
@Table
@Getter
@Setter
public class Post extends BaseEntity {

    @Column(name = "text")
    private String text;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;
}
