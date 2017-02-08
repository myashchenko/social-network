package ua.social.network.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author Mykola Yashchenko
 */
@Getter
@Setter
@Entity
@Table(name = "user_wall")
public class UserWall extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false, mappedBy = "wall")
    @JoinColumn(name = "user_id", unique = true, nullable = false, updatable = false)
    private User user;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_wall_id")
    private List<Post> posts;
}
