package ua.social.network.oauth2.exception;

import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultOAuth2ExceptionRenderer;
import org.springframework.web.client.RestTemplate;

import ua.social.network.api.Error;
import ua.social.network.api.ErrorResponse;

/**
 * @author Mykola Yashchenko
 */
public class SnOAuth2ExceptionRenderer extends DefaultOAuth2ExceptionRenderer {

    public SnOAuth2ExceptionRenderer() {
        setMessageConverters(messageConverters());
    }

    private List<HttpMessageConverter<?>> messageConverters() {
        final List<HttpMessageConverter<?>> result = new ArrayList<>(new RestTemplate().getMessageConverters());
        result.add(0, new GsonHttpMessageConverter() {

            @Override
            protected void writeInternal(final Object object, final Type type, final Writer writer) {
                final OAuth2Exception ex = (OAuth2Exception) object;
                getGson().toJson(new ErrorResponse(new Error(ex.getMessage(), ex.getOAuth2ErrorCode())), writer);
            }
        });
        return result;
    }
}
