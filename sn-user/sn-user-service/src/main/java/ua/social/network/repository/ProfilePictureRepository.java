package ua.social.network.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.social.network.entity.ProfilePicture;

/**
 * @author Mykola Yashchenko
 */
public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, String> {
    ProfilePicture findByUserId(String userId);
}
