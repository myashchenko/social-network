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
@Table(name = "community_wall")
public class CommunityWall extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false, mappedBy = "wall")
    @JoinColumn(name = "community_id", unique = true, nullable = false, updatable = false)
    private Community community;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "community_wall_id")
    private List<Post> posts;
}
