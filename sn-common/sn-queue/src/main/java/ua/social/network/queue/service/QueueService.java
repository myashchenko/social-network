package ua.social.network.queue.service;

import java.util.stream.Stream;

import ua.social.network.queue.api.Message;

/**
 * @author Mykola Yashchenko
 */
public interface QueueService {
    void send(Message message);

    Stream<Message> receive();
}
