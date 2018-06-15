package ua.social.network.message.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.repository.query.Param;

import reactor.core.publisher.Mono;
import ua.social.network.message.entity.User;

/**
 * @author Mykola Yashchenko
 */
public interface UserRepository extends ReactiveCassandraRepository<User, String> {
    @Query("SELECT count(*) FROM users WHERE id IN (:ids)")
    Mono<Long> findAllByIds(@Param("ids") List<String> ids);
}
