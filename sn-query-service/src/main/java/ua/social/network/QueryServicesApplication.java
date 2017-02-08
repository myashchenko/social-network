package ua.social.network;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Mykola Yashchenko
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackageClasses = QueryServicesApplication.class)
public class QueryServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(QueryServicesApplication.class, args);
    }
}
