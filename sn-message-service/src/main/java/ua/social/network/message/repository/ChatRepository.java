package ua.social.network.message.repository;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import ua.social.network.message.entity.Chat;

/**
 * @author Mykola Yashchenko
 */
public interface ChatRepository extends ReactiveCassandraRepository<Chat, String> {
}
