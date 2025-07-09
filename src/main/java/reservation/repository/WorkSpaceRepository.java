package reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reservation.entity.WorkSpace;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkSpaceRepository extends JpaRepository<WorkSpace, Integer> {
    List<WorkSpace> findByAvailabilityTrue();
}
