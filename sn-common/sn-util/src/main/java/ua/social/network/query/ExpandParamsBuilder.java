package ua.social.network.query;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Mykola Yashchenko
 */
public class ExpandParamsBuilder {

    private static final String EXPAND = "expand";

    private String[] params;

    public ExpandParamsBuilder(String params) {
        Objects.requireNonNull(params);
        if (params.isEmpty()) {
            this.params = new String[0];
        } else {
            this.params = params.split(",");
        }
    }

    public Map<String, Object> build() {
        Map<String, Object> params = new HashMap<>();

        for (String param : this.params) {
            param = param.trim();
            String key = EXPAND + Character.toUpperCase(param.charAt(0)) + param.substring(1).toLowerCase();
            params.put(key, Boolean.TRUE);
        }

        return params;
    }
}
