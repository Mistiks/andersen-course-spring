package reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reservation.entity.Reservation;
import reservation.entity.WorkSpace;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    void deleteAllByWorkSpace(WorkSpace space);
}
