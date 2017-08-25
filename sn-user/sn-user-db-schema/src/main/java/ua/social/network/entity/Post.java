package ua.social.network.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Mykola Yashchenko
 */
@Getter
@Setter
@Entity
@Table(name = "post")
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
