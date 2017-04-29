package ua.social.network.entity;

import lombok.Getter;
import lombok.Setter;
import ua.social.network.listener.IdGeneratorListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Mykola Yashchenko
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(IdGeneratorListener.class)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BaseEntity {
    @Id
    private String id;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;
}
