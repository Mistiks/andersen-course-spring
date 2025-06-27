package repository;

import entity.Reservation;
import entity.WorkSpace;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    public int addNewReservation(Reservation reservation) {
        Optional<WorkSpace> workSpace = workSpaceRepository.getWorkSpaceById(reservation.getSpaceId());
        EntityTransaction transaction;

        if (workSpace.isEmpty()) {
            return 0;
        }

        reservation = new Reservation(reservation.getId(), workSpace.get(), reservation.getClientName(),
                reservation.getDate(), reservation.getTimeStart(), reservation.getTimeEnd());

        transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(reservation);
        transaction.commit();

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

    public int deleteReservation(int id) {
        Optional<Reservation> reservation = getReservationById(id);

        if (reservation.isPresent()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.remove(reservation.get());

            entityManager.flush();
            transaction.commit();
            return 1;
        }

        return 0;
    }

    public int deleteReservationByWorkSpaceId(int spaceId) {
        Optional<WorkSpace> workSpace = workSpaceRepository.getWorkSpaceById(spaceId);
        List<Reservation> reservationList;
        EntityTransaction transaction;

        if (workSpace.isEmpty()) {
            return 0;
        }

        reservationList = getAllReservationsByWorkSpaceId(spaceId);

        transaction = entityManager.getTransaction();
        transaction.begin();

        for (Reservation reservation : reservationList) {
            entityManager.remove(reservation);
        }

        entityManager.flush();
        transaction.commit();

        return reservationList.size();
    }

    private List<Reservation> getAllReservationsByWorkSpaceId(int spaceId) {
        return entityManager.createQuery("SELECT e FROM Reservation e WHERE e.space_id = :id", Reservation.class)
                .setParameter("id", spaceId)
                .getResultList();
    }
}
