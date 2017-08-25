package ua.social.network.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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

    @Column(name = "user_id", nullable = false)
    private String userId;
}
