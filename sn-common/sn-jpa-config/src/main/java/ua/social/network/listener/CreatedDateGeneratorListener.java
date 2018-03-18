package ua.social.network.listener;

import java.time.LocalDateTime;
import javax.persistence.PrePersist;

import ua.social.network.entity.BaseEntity;

/**
 * @author Mykola Yashchenko
 */
public class CreatedDateGeneratorListener {

    @PrePersist
    public void setCreatedDate(BaseEntity baseEntity) {
        baseEntity.setCreated(LocalDateTime.now());
    }
}
