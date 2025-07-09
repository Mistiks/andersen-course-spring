package reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reservation.entity.UserProfile;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    Optional<UserProfile> findByUsername(String username);
}
