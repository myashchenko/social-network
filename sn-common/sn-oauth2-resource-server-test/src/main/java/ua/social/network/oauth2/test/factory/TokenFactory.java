package ua.social.network.oauth2.test.factory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

/**
 * @author Mykola Yashchenko
 */
@Component
public class TokenFactory implements InitializingBean {

    @Value("${security.oauth2.key_store.path}")
    private String keyStorePath;

    @Value("${security.oauth2.key_pair_alias}")
    private String keyStoreAlias;

    @Value("${security.oauth2.key_store.password}")
    private String keyStorePassword;

    private final ClientDetailsService clientDetailsService;
    private final ResourceLoader resourceLoader;

    private AuthorizationServerTokenServices tokenServices;

    public TokenFactory(final ClientDetailsService clientDetailsService,
                        final ResourceLoader resourceLoader) {
        this.clientDetailsService = clientDetailsService;
        this.resourceLoader = resourceLoader;
    }

    public RequestPostProcessor token(final String userId, final String scope) {
        return mockRequest -> {
            final OAuth2AccessToken token = createAccessToken(mockRequest, userId, "test", scope);
            mockRequest.addHeader("Authorization", "Bearer " + token.getValue());
            return mockRequest;
        };
    }

    private OAuth2AccessToken createAccessToken(final HttpServletRequest request, final String userId,
                                                final String clientId, final String scope) {
        final ClientDetails client = clientDetailsService.loadClientByClientId(clientId);

        final OAuth2Request oAuth2Request = new OAuth2Request(Collections.emptyMap(), clientId, client.getAuthorities(),
                true, Set.of(scope), client.getResourceIds(), null, Collections.emptySet(), Collections.emptyMap());

        final User userPrincipal = new User("user", "", true, true, true, true, client.getAuthorities());
        final UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userPrincipal, null, client.getAuthorities());
        final OAuth2Authentication auth = new OAuth2Authentication(oAuth2Request, authenticationToken);
        final OAuth2AuthenticationDetails details = new OAuth2AuthenticationDetails(request);
        auth.setDetails(details);

        details.setDecodedDetails(Map.of("user_id", userId));

        return tokenServices.createAccessToken(auth);
    }

    @Override
    public void afterPropertiesSet() {
        this.tokenServices = tokenServices();
    }

    private DefaultTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(List.of(tokenEnhancer(), accessTokenConverter()));
        defaultTokenServices.setTokenEnhancer(tokenEnhancerChain);
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    private TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    private JwtAccessTokenConverter accessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        final KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                resourceLoader.getResource(keyStorePath), keyStorePassword.toCharArray()
        );
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair(keyStoreAlias));
        return converter;
    }

    @SuppressWarnings("unchecked")
    private TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            final OAuth2AuthenticationDetails oauth2Details = (OAuth2AuthenticationDetails) authentication.getDetails();
            ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation((Map<String, Object>) oauth2Details.getDecodedDetails());
            return accessToken;
        };
    }
}
