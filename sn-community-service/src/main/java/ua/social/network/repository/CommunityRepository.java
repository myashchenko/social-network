package ua.social.network.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.social.network.entity.Community;

/**
 * @author Mykola Yashchenko
 */
public interface CommunityRepository extends JpaRepository<Community, String> {
}
