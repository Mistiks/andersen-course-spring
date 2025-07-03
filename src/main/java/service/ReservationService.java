package service;

import entity.Reservation;
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

    public List<String> getAllReservations() {
        return reservationRepository.getAllReservations().stream().map(Reservation::toString).toList();
    }

    public Optional<Reservation> getReservationById(int id) {
        return Optional.ofNullable(reservationRepository.getReservationById(id));
    }
}
