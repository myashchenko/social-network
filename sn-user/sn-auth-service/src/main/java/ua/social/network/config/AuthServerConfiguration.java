package ua.social.network.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import ua.social.network.oauth2.SnTokenEnhancer;
import ua.social.network.service.SnUserDetailsService;

/**
 * @author Mykola Yashchenko
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final SnUserDetailsService userDetailsService;
    private final ResourceLoader resourceLoader;
    private final Environment environment;

    @Value("${security.oauth2.key_store.path}")
    private String keyStorePath;

    @Value("${security.oauth2.key_pair_alias}")
    private String keyStoreAlias;

    @Value("${security.oauth2.key_store.password}")
    private String keyStorePassword;

    public AuthServerConfiguration(@Qualifier("authenticationManagerBean") final AuthenticationManager authenticationManager,
                                   final SnUserDetailsService userDetailsService, final ResourceLoader resourceLoader,
                                   final Environment environment) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.resourceLoader = resourceLoader;
        this.environment = environment;
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {

        // TODO persist clients details

        // @formatter:off
        clients
                .inMemory()
                    .withClient("browser")
                    .secret("{noop}secret")
                    .authorizedGrantTypes("refresh_token", "password")
                    .scopes("ui")
                .and()
                    .withClient("sn-user-query-service")
                    .secret(environment.getProperty("security.oauth2.client.sn-user-query-service"))
                    .authorizedGrantTypes("client_credentials", "refresh_token")
                    .scopes("server")
                .and()
                    .withClient("sn-user-service")
                    .secret(environment.getProperty("security.oauth2.client.sn-user-service"))
                    .authorizedGrantTypes("client_credentials", "refresh_token")
                    .scopes("server")
                .and()
                    .withClient("sn-storage-service")
                    .secret(environment.getProperty("security.oauth2.client.sn-storage-service"))
                    .authorizedGrantTypes("client_credentials", "refresh_token")
                    .scopes("server");
        // @formatter:on
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));

        endpoints
                .tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancerChain)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .accessTokenConverter(accessTokenConverter());
    }

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        final KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                resourceLoader.getResource(keyStorePath), keyStorePassword.toCharArray()
        );
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair(keyStoreAlias));
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new SnTokenEnhancer();
    }
}