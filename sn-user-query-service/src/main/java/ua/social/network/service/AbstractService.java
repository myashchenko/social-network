package ua.social.network.service;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.social.network.query.Mapper;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mykola Yashchenko
 */
public abstract class AbstractService<ENTITY, MAPPER extends Mapper<ENTITY>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);

    private static final String ID = "id";

    private MAPPER mapper;

    public AbstractService(MAPPER mapper) {
        this.mapper = mapper;
    }

    public ENTITY getEntity(String id) {
        return mapper.getSingle(Collections.singletonMap(ID, id));
    }

    public List<ENTITY> getEntityList(Object key) {
        Map<String, Object> describe = new HashMap<>(getProperties(key));
        return mapper.getList(describe);
    }

    public List<ENTITY> getEntityList(Map<String, Object> params) {
        return mapper.getList(params);
    }

    protected Map<String, String> getProperties(Object object) {
        try {
            return BeanUtils.describe(object);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            LOGGER.error("Can't get properties of object", e);
        }
        return Collections.emptyMap();
    }
}
