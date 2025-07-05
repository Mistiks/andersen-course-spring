package repository;

import entity.Reservation;
import entity.WorkSpace;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ReservationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public int addNewReservation(Reservation reservation) {
        entityManager.persist(reservation);
        return 1;
    }

    public Reservation getReservationById(int id) {
        return entityManager.find(Reservation.class, id);
    }

    public List<Reservation> getAllReservations() {
        return entityManager.createQuery("SELECT e FROM Reservation e", Reservation.class).getResultList();
    }

    @Transactional
    public int deleteReservation(int id) {
        Reservation reservation = getReservationById(id);

        if (reservation != null) {
            entityManager.remove(reservation);
            entityManager.flush();

            return 1;
        }

        return 0;
    }

    @Transactional
    public int deleteReservationByWorkSpaceId(WorkSpace space) {
        List<Reservation> reservationList;

        reservationList = getAllReservationsByWorkSpaceId(space);

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
