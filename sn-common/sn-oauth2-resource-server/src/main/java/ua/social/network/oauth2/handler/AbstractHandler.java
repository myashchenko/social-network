package ua.social.network.oauth2.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.OAuth2ExceptionRenderer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import ua.social.network.oauth2.exception.SnOAuth2ExceptionRenderer;

/**
 * @author Mykola Yashchenko
 */
public abstract class AbstractHandler {

    private WebResponseExceptionTranslator exceptionTranslator = new DefaultWebResponseExceptionTranslator();
    private HandlerExceptionResolver handlerExceptionResolver = new DefaultHandlerExceptionResolver();
    private OAuth2ExceptionRenderer exceptionRenderer = new SnOAuth2ExceptionRenderer();

    protected void handle(final HttpServletRequest request, final HttpServletResponse response,
                          final Exception authException) throws ServletException, IOException {
        try {
            final ResponseEntity<OAuth2Exception> result = exceptionTranslator.translate(authException);
            exceptionRenderer.handleHttpEntityResponse(result, new ServletWebRequest(request, response));
            response.flushBuffer();
        } catch (final ServletException e) {
            if (handlerExceptionResolver.resolveException(request, response, this, e) == null) {
                throw e;
            }
        } catch (final IOException | RuntimeException e) {
            throw e;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
