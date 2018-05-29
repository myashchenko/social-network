package ua.social.network.oauth2.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.OAuth2ExceptionRenderer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import ua.social.network.oauth2.exception.SnOAuth2ExceptionRenderer;

/**
 * @author Mykola Yashchenko
 */
public class SnAccessDeniedHandler implements AccessDeniedHandler {

    private WebResponseExceptionTranslator exceptionTranslator = new DefaultWebResponseExceptionTranslator();
    private HandlerExceptionResolver handlerExceptionResolver = new DefaultHandlerExceptionResolver();
    private OAuth2ExceptionRenderer exceptionRenderer = new SnOAuth2ExceptionRenderer();

    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response,
                       final AccessDeniedException accessDeniedException) throws IOException, ServletException {
        try {
            final ResponseEntity<OAuth2Exception> result = exceptionTranslator.translate(accessDeniedException);
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
