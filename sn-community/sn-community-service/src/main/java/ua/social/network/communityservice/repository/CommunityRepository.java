package ua.social.network.communityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.social.network.communityservice.entity.Community;

/**
 * @author Mykola Yashchenko
 */
public interface CommunityRepository extends JpaRepository<Community, String> {
}
