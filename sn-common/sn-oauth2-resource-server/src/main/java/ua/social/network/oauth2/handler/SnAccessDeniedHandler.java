package ua.social.network.oauth2.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * @author Mykola Yashchenko
 */
public class SnAccessDeniedHandler extends AbstractHandler implements AccessDeniedHandler {

    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response,
                       final AccessDeniedException accessDeniedException) throws IOException, ServletException {
        super.handle(request, response, accessDeniedException);
    }
}
