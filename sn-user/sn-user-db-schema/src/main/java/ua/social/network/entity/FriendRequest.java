package ua.social.network.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Mykola Yashchenko
 */
@Getter
@Setter
@Entity
@Table(name = "friend_request")
public class FriendRequest extends BaseEntity {

    @OneToOne(optional = false)
    @JoinColumn(name = "from_id", nullable = false)
    private User from;

    @OneToOne(optional = false)
    @JoinColumn(name = "to_id", nullable = false)
    private User to;

}
