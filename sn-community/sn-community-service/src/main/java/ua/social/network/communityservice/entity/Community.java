package ua.social.network.communityservice.entity;

import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ua.social.network.entity.BaseEntity;

/**
 * @author Mykola Yashchenko
 */
@Getter
@Setter
@Entity
@Table(name = "communities")
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
}
