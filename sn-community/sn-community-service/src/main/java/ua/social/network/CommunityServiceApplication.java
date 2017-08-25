package ua.social.network;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import ua.social.network.security.CustomUserInfoTokenServices;

/**
 * @author Mykola Yashchenko
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableResourceServer
@EnableOAuth2Client
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackageClasses = CommunityServiceApplication.class)
public class CommunityServiceApplication {

    @Autowired
    private ResourceServerProperties sso;

    public static void main(String[] args) {
        SpringApplication.run(CommunityServiceApplication.class, args);
    }

    @Bean
    public ResourceServerTokenServices tokenServices() {
        return new CustomUserInfoTokenServices(sso.getUserInfoUri(), sso.getClientId());
    }
}
