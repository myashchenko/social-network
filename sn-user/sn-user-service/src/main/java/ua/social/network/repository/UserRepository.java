package ua.social.network.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ua.social.network.entity.Role;
import ua.social.network.entity.User;

/**
 * @author Mykola Yashchenko
 */
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    @Modifying
    @Query("update User u set u.role = :roleType where u.id = :userId")
    int updateRole(@Param("userId") String userId, @Param("roleType") Role roleType);

    @Modifying
    @Query("update User u set u.avatarId = :avatarId where u.id = :userId")
    int modify(@Param("userId") String userId, @Param("avatarId") String avatarId);
}
