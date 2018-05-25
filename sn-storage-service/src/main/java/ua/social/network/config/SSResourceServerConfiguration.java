package ua.social.network.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import ua.social.network.oauth2.converter.SnAccessTokenConverter;

/**
 * @author Mykola Yashchenko
 */
@Configuration
public class SSResourceServerConfiguration extends ua.social.network.oauth2.config.ResourceServerConfiguration {

    public SSResourceServerConfiguration(final SnAccessTokenConverter accessTokenConverter,
                                         final ResourceLoader resourceLoader) {
        super(accessTokenConverter, resourceLoader);
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/files").authenticated()
                .antMatchers(HttpMethod.GET, "/api/files/**").permitAll()
                .anyRequest().authenticated();
    }
}
