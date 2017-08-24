package ua.social.network.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.social.network.entity.User;

/**
 * @author Mykola Yashchenko
 */
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
}
