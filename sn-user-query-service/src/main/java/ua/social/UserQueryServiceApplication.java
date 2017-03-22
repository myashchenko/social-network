package ua.social;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Mykola Yashchenko
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackageClasses = UserQueryServiceApplication.class)
public class UserQueryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserQueryServiceApplication.class, args);
    }
}
