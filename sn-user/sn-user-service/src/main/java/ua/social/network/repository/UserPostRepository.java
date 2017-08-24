package ua.social.network.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.social.network.entity.Post;

/**
 * @author Mykola Yashchenko
 */
public interface UserPostRepository extends JpaRepository<Post, String> {
}
