package ua.social.network.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Mykola Yashchenko
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(value = {"it.ozimov.springboot.mail.templating", "ua.social.network.notification"})
public class NotificationServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
}
