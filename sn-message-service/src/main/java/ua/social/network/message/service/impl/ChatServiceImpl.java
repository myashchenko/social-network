package ua.social.network.message.service.impl;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ua.social.network.message.domain.CreateChatRequest;
import ua.social.network.message.domain.CreateChatResponse;
import ua.social.network.message.entity.Chat;
import ua.social.network.message.repository.ChatRepository;
import ua.social.network.message.repository.UserRepository;
import ua.social.network.message.service.ChatService;

/**
 * @author Mykola Yashchenko
 */
@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Override
    public Mono<CreateChatResponse> create(final Mono<CreateChatRequest> request) {
        return request.publishOn(Schedulers.parallel())
                .flatMap(this::validate)
                .map(req -> new Chat(req.getTitle(), req.getUserIds()))
                .flatMap(chatRepository::insert)
                .map(chat -> new CreateChatResponse(chat.getId()));
    }

    private Mono<CreateChatRequest> validate(final CreateChatRequest request) {
        return userRepository.findAllByIds(request.getUserIds())
                .filter(count -> count == request.getUserIds().size())
                .flatMap(count -> Mono.just(request));
    }
}
