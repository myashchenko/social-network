package ua.social.network.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

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

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "user_id")
    @ElementCollection
    @CollectionTable(name = "followers", joinColumns = @JoinColumn(name = "community_id"))
    private List<String> followers;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(name = "post_community_assoc", joinColumns = @JoinColumn(name = "community_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    private List<Post> posts;
}
