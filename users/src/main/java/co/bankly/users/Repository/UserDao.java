package co.bankly.users.Repository;

import co.bankly.users.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email=:q or u.username=:q")
    Optional<User> findByUsernameOrEmail(@Param("q") String q);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUuid(UUID uuid);

    User findByEmail(String email);

    int deleteByUuid(UUID uuid1);

    int deleteByEmail(String email);
}
