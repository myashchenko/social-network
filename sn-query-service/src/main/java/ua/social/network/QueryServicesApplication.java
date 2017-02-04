package ua.social.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOG = LoggerFactory.getLogger(QueryServicesApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(QueryServicesApplication.class, args);
    }
}
