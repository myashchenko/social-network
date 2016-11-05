package ua.social.network.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author Mykola Yashchenko
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public class BaseEntity {
    @Id
    private Long id;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;
}
