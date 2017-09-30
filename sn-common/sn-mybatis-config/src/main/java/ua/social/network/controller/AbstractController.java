package ua.social.network.controller;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.social.network.query.ExpandParamsBuilder;
import ua.social.network.query.Mapper;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mykola Yashchenko
 */
public abstract class AbstractController<ENTITY, MAPPER extends Mapper<ENTITY>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);

    private static final String ID = "id";
    private static final String EXPAND = "expand";

    private final MAPPER mapper;

    public AbstractController(MAPPER mapper) {
        this.mapper = mapper;
    }

    public ENTITY getEntity(String id, Map<String, Object> params) {
        params.put(ID, id);
        params = getWithExpandParams(params);
        return mapper.getSingle(params);
    }

    public List<ENTITY> getEntityList(Map<String, Object> params) {
        params = getWithExpandParams(params);
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

    private Map<String, Object> getWithExpandParams(Map<String, Object> params) {
        if (params.get(EXPAND) != null) {
            params = new HashMap<>(params);
            Map<String, Object> expandParams = new ExpandParamsBuilder(params.get(EXPAND).toString()).build();
            params.putAll(expandParams);
        }
        return params;
    }
}
