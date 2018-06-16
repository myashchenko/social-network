package ua.social.network.messageservice.ws;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * @author Mykola Yashchenko
 */
@Service
@AllArgsConstructor
public class ChatHandler implements WebSocketHandler {

    private final WebSocketMessageMapper webSocketMessageMapper;

    @Override
    public Mono<Void> handle(final WebSocketSession session) {
        return session.receive()
                .map(WebSocketMessage::retain)
                .map(WebSocketMessage::getPayload)
                .publishOn(Schedulers.parallel())
                .transform(webSocketMessageMapper::decode)
                .transform(this::doHandle)
                .onBackpressureBuffer()
                .transform(m -> webSocketMessageMapper.encode(m, session.bufferFactory()))
                .map(db -> new WebSocketMessage(WebSocketMessage.Type.TEXT, db))
                .as(session::send);

    }

    private Flux<?> doHandle(final Flux<WsEvent> inbound) {
        return Flux.empty();
    }
}
