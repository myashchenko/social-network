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
@Table(name = "community")
public class Community extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "communities")
    private List<User> followers;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wall_id", unique = true, updatable = false)
    private CommunityWall wall;
}
