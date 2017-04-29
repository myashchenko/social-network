package ua.social.network.listener;

import ua.social.network.entity.BaseEntity;

import javax.persistence.PrePersist;
import java.util.UUID;

/**
 * @author Mykola Yashchenko
 */
public class IdGeneratorListener {

    @PrePersist
    public void generatedId(BaseEntity baseEntity) {
        baseEntity.setId(UUID.randomUUID().toString());
    }
}
