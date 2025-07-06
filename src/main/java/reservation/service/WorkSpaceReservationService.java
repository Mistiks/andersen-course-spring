package reservation.service;

import reservation.entity.Reservation;
import reservation.entity.WorkSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reservation.repository.ReservationRepository;
import reservation.repository.WorkSpaceRepository;

import java.util.Optional;

@Service
public class WorkSpaceReservationService {

    private final WorkSpaceRepository workSpaceRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public WorkSpaceReservationService(WorkSpaceRepository workSpaceRepository, ReservationRepository reservationRepository) {
        this.workSpaceRepository = workSpaceRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public void addReservation(Reservation reservation) {
        int status;
        Optional<WorkSpace> workSpace = workSpaceRepository.findById(reservation.getSpaceId());

        if (workSpace.isPresent()) {
            reservation = new Reservation(reservation.getId(), workSpace.get(), reservation.getClientName(),
                    reservation.getDate(), reservation.getTimeStart(), reservation.getTimeEnd());
            status = reservationRepository.save(reservation).getId();

            if (status == reservation.getId()) {
                workSpace.get().setAvailability(false);
                workSpaceRepository.save(workSpace.get());
            }
        }
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
