package repository;

import entity.Reservation;
import entity.WorkSpace;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class ReservationRepository {

    private final WorkSpaceRepository workSpaceRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ReservationRepository(WorkSpaceRepository workSpaceRepository) {
        this.workSpaceRepository = workSpaceRepository;
    }

    @Transactional
    public int addNewReservation(Reservation reservation) {
        Optional<WorkSpace> workSpace = workSpaceRepository.getWorkSpaceById(reservation.getSpaceId());

        if (workSpace.isEmpty()) {
            return 0;
        }

        reservation = new Reservation(reservation.getId(), workSpace.get(), reservation.getClientName(),
                reservation.getDate(), reservation.getTimeStart(), reservation.getTimeEnd());

        entityManager.persist(reservation);

        return 1;
    }

    public Optional<Reservation> getReservationById(int id) {
        Reservation reservation = entityManager.find(Reservation.class, id);

        if (reservation != null) {
            return Optional.of(reservation);
        } else {
            return Optional.empty();
        }
    }

    public List<Reservation> getAllReservations() {
        return entityManager.createQuery("SELECT e FROM Reservation e", Reservation.class).getResultList();
    }

    @Transactional
    public int deleteReservation(int id) {
        Optional<Reservation> reservation = getReservationById(id);

        if (reservation.isPresent()) {
            entityManager.remove(reservation.get());
            entityManager.flush();

            return 1;
        }

        return 0;
    }

    @Transactional
    public int deleteReservationByWorkSpaceId(int spaceId) {
        Optional<WorkSpace> workSpace = workSpaceRepository.getWorkSpaceById(spaceId);
        List<Reservation> reservationList;

        if (workSpace.isEmpty()) {
            return 0;
        }

        reservationList = getAllReservationsByWorkSpaceId(workSpace.get());

        for (Reservation reservation : reservationList) {
            entityManager.remove(reservation);
        }

        entityManager.flush();

        return reservationList.size();
    }

    private List<Reservation> getAllReservationsByWorkSpaceId(WorkSpace space) {
        return entityManager.createQuery("SELECT e FROM Reservation e WHERE e.workSpace = :id", Reservation.class)
                .setParameter("id", space)
                .getResultList();
    }
}
