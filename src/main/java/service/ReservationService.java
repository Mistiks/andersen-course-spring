package service;

import entity.Reservation;
import entity.WorkSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ReservationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public int addNewReservation(Reservation reservation) {
        return reservationRepository.addNewReservation(reservation);
    }

    public Optional<Reservation> getReservationById(int id) {
        return reservationRepository.getReservationById(id);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.getAllReservations();
    }

    public int deleteReservation(int id) {
        return reservationRepository.deleteReservation(id);
    }

    public int deleteReservationByWorkSpaceId(int spaceId) {
        return reservationRepository.deleteReservationByWorkSpaceId(spaceId);
    }
}
