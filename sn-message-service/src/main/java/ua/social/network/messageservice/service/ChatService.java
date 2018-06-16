package ua.social.network.messageservice.service;

import reactor.core.publisher.Mono;
import ua.social.network.messageservice.domain.CreateChatRequest;
import ua.social.network.messageservice.domain.CreateChatResponse;

/**
 * @author Mykola Yashchenko
 */
public interface ChatService {
    Mono<CreateChatResponse> create(final Mono<CreateChatRequest> request);
}
