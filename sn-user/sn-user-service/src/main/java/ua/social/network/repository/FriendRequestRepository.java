package ua.social.network.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ua.social.network.entity.FriendRequest;
import ua.social.network.entity.User;

/**
 * @author Mykola Yashchenko
 */
public interface FriendRequestRepository extends JpaRepository<FriendRequest, String> {
    @Query("select req from FriendRequest req where (req.from = ?1 and req.to = ?2) or (req.from = ?2 and req.to = ?1)")
    FriendRequest findRequestByUsers(User user1, User user2);

    FriendRequest findByIdAndToId(String id, String toId);

    @Query("delete from FriendRequest req where req.id = :id and ((req.to.id = :userId) or (req.from.id = :userId))")
    Long deleteByIdAndToIdOrIdAndFromId(@Param("id") String id, @Param("userId") String userId);
}
