package ua.social.network;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.stereotype.Controller;

/**
 * @author Mykola Yashchenko
 */
@SpringBootApplication
@Controller
@EnableZuulProxy
public class ZuulApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ZuulApplication.class).web(WebApplicationType.SERVLET).run(args);
    }
}