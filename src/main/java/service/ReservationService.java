package service;

import entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ReservationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public String getAllReservations() {
        List<Reservation> reservationList = reservationRepository.getAllReservations();
        String reservationsData = reservationList.stream()
                .map(Reservation::toString)
                .collect(Collectors.joining("\n", "", "\n"));

        if (reservationsData.isEmpty()) {
            return "Reservations not found!\n";
        }

        return reservationsData;
    }
}
