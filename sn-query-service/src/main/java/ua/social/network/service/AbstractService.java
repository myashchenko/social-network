package ua.social.network.service;

import ua.social.network.query.Mapper;

import java.util.Collections;
import java.util.List;

/**
 * @author Mykola Yashchenko
 */
public abstract class AbstractService<ENTITY, MAPPER extends Mapper<ENTITY>> {

    private static final String ID = "id";

    private MAPPER mapper;

    public ENTITY getEntity(String id) {
        return mapper.getSingle(Collections.singletonMap(ID, id));
    }

    public List<ENTITY> getEntityList(Object key) {
        // todo
        return mapper.getList(Collections.emptyMap());
    }
}
