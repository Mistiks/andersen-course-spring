package reservation.service;

import reservation.entity.Reservation;
import reservation.entity.WorkSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reservation.model.ReservationModel;
import reservation.repository.ReservationRepository;
import reservation.repository.WorkSpaceRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Service
public class WorkSpaceReservationService {

    private final WorkSpaceRepository workSpaceRepository;
    private final ReservationRepository reservationRepository;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    @Autowired
    public WorkSpaceReservationService(WorkSpaceRepository workSpaceRepository, ReservationRepository reservationRepository) {
        this.workSpaceRepository = workSpaceRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public int addReservation(ReservationModel reservation, WorkSpace space) {
        int status;
        LocalDate date;
        LocalTime timeStart;
        LocalTime timeEnd;
        Reservation newReservation;

        try {
            date = LocalDate.parse(reservation.getDate(), dateFormatter);
            timeStart = LocalTime.parse(reservation.getTimeStart(), timeFormatter);
            timeEnd = LocalTime.parse(reservation.getTimeEnd(), timeFormatter);
        } catch (DateTimeParseException exception) {
            return 0;
        }

        newReservation = new Reservation(reservation.getId(), space, reservation.getClientName(),
                date, timeStart, timeEnd);
        status = reservationRepository.save(newReservation).getId();

        if (status == reservation.getId()) {
            space.setAvailability(false);
            return workSpaceRepository.save(space).getId();
        }

        return 0;
    }

    @Transactional
    public void deleteWorkSpace(int spaceId) {
        Optional<WorkSpace> workSpace = workSpaceRepository.findById(spaceId);

        if (workSpace.isEmpty()) {
            return;
        }

        reservationRepository.deleteAllByWorkSpace(workSpace.get());
        workSpaceRepository.deleteById(spaceId);
    }

    @Transactional
    public int deleteReservation(int reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);

        if (reservation.isEmpty()) {
            return 0;
        }

        Optional<WorkSpace> workSpace = workSpaceRepository.findById(reservation.get().getSpaceId());

        if (workSpace.isPresent()) {
            workSpace.get().setAvailability(true);
            workSpaceRepository.save(workSpace.get());
        }

        reservationRepository.deleteById(reservationId);

        return 1;
    }
}
