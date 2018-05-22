package ua.social.network.oauth2.principal;

import java.security.Principal;
import java.util.Map;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

/**
 * @author Mykola Yashchenko
 */
public class SnPrincipal {
    public final String userId;

    @SuppressWarnings("unchecked")
    public SnPrincipal(final Principal principal) {
        if (!(principal instanceof OAuth2Authentication)) {
            // todo throw appropriate execption
            throw new RuntimeException();
        }

        final Map<String, Object> details = (Map<String, Object>)
                ((OAuth2AuthenticationDetails) ((OAuth2Authentication) principal).getDetails()).getDecodedDetails();

        this.userId = (String) details.get("user_id");
    }
}
