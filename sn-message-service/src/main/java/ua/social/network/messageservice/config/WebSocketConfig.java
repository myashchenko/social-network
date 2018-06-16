package ua.social.network.messageservice.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerAdapter;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import ua.social.network.messageservice.ws.ChatHandler;

/**
 * @author Mykola Yashchenko
 */
@Configuration
public class WebSocketConfig {

    @Bean
    public HandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    public HandlerMapping webSocketHandler(final ChatHandler channel) {
        final SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        final Map<String, WebSocketHandler> urlMap = Map.of("/chats", channel);

        mapping.setUrlMap(urlMap);
        mapping.setOrder(0);

        return mapping;
    }
}
