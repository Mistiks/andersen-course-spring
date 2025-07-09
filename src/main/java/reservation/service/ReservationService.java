package reservation.service;

import reservation.entity.Reservation;
import reservation.model.ReservationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reservation.repository.ReservationRepository;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationModel> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(i -> new ReservationModel(i.getId(), i.getSpaceId(), i.getClientName(),
                        i.getDate().format(dateFormatter), i.getTimeStart().format(timeFormatter),
                        i.getTimeEnd().format(timeFormatter)))
                .toList();
    }

    public Optional<Reservation> getReservationById(int id) {
        return reservationRepository.findById(id);
    }
}
