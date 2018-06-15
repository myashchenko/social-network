package ua.social.network.message.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import ua.social.network.message.domain.CreateChatRequest;
import ua.social.network.message.service.ChatService;

/**
 * @author Mykola Yashchenko
 */
@RestController
@AllArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/chats")
    public Mono<ServerResponse> create(final ServerRequest request) {
        final Mono<CreateChatRequest> createChatRequestMono = request.bodyToMono(CreateChatRequest.class);

        return chatService.create(createChatRequestMono)
                .flatMap(response ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromObject(response))
                );
    }
}
