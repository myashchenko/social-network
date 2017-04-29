package ua.social.network.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Mykola Yashchenko
 */
@Entity
@Table(name = "post")
@Getter
@Setter
public class Post extends BaseEntity {

    @Column(name = "text")
    private String text;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false, updatable = false)
    private User sender;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false, updatable = false)
    private User receiver;
}
