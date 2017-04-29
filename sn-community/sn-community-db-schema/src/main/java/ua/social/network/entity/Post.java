package ua.social.network.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
}
