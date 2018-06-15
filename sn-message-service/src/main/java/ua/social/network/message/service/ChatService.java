package ua.social.network.message.service;

import reactor.core.publisher.Mono;
import ua.social.network.message.domain.CreateChatRequest;
import ua.social.network.message.domain.CreateChatResponse;

/**
 * @author Mykola Yashchenko
 */
public interface ChatService {
    Mono<CreateChatResponse> create(final Mono<CreateChatRequest> request);
}
