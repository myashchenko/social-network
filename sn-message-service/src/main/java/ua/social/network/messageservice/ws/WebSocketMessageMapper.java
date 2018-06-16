package ua.social.network.messageservice.ws;

import java.util.Collections;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Mykola Yashchenko
 */
@Service
public class WebSocketMessageMapper {

    private final Jackson2JsonEncoder encoder;
    private final Jackson2JsonDecoder decoder;

    public WebSocketMessageMapper(final ObjectMapper objectMapper) {
        encoder = new Jackson2JsonEncoder(objectMapper);
        decoder = new Jackson2JsonDecoder(objectMapper);
    }

    public Flux<DataBuffer> encode(final Flux<?> outbound, final DataBufferFactory dataBufferFactory) {
        return outbound
                .flatMap(i -> encoder.encode(
                        Mono.just(i),
                        dataBufferFactory,
                        ResolvableType.forType(Object.class),
                        MediaType.APPLICATION_JSON,
                        Collections.emptyMap()
                ));

    }

    @SuppressWarnings("unchecked")
    public Flux<WsEvent> decode(final Flux<DataBuffer> inbound) {
        return inbound.flatMap(p -> decoder.decode(
                Mono.just(p),
                ResolvableType.forType(new ParameterizedTypeReference<WsEvent>() {
                }),
                MediaType.APPLICATION_JSON,
                Collections.emptyMap()
        )).map(o -> (WsEvent) o);
    }
}
