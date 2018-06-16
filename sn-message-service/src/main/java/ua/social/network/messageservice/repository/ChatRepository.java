package ua.social.network.messageservice.repository;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import ua.social.network.messageservice.entity.Chat;

/**
 * @author Mykola Yashchenko
 */
public interface ChatRepository extends ReactiveCassandraRepository<Chat, String> {
}
