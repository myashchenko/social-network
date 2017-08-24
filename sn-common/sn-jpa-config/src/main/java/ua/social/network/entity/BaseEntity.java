package ua.social.network.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import ua.social.network.listener.CreatedDateGeneratorListener;
import ua.social.network.listener.IdGeneratorListener;

/**
 * @author Mykola Yashchenko
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners({IdGeneratorListener.class, CreatedDateGeneratorListener.class})
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BaseEntity {
    @Id
    private String id;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity that = (BaseEntity) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
