package ua.social.network.query;

import java.util.List;
import java.util.Map;

/**
 * @author Mykola Yashchenko
 */
public interface Mapper<ENTITY> {
    ENTITY getSingle(Map<String, Object> params);
    List<ENTITY> getList(Map<String, Object> params);
}
