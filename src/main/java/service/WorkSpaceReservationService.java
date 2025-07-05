package service;

import entity.Reservation;
import entity.WorkSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.ReservationRepository;
import repository.WorkSpaceRepository;

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
    public int addReservation(Reservation reservation) {
        int status;
        WorkSpace workSpace = workSpaceRepository.getWorkSpaceById(reservation.getSpaceId());

        if (workSpace != null) {
            reservation = new Reservation(reservation.getId(), workSpace, reservation.getClientName(),
                    reservation.getDate(), reservation.getTimeStart(), reservation.getTimeEnd());
            status = reservationRepository.addNewReservation(reservation);

            if (status == 1) {
                workSpace.setAvailability(false);
                workSpaceRepository.updateWorkSpace(workSpace);
            }

            return status;
        }

        return 0;
    }

    public int deleteWorkSpace(int spaceId) {
        WorkSpace workSpace = workSpaceRepository.getWorkSpaceById(spaceId);

        if (workSpace == null) {
            return 0;
        }

        reservationRepository.deleteReservationByWorkSpaceId(workSpace);
        return workSpaceRepository.deleteWorkspace(spaceId);
    }

    public int deleteReservation(int reservationId) {
        Reservation reservation = reservationRepository.getReservationById(reservationId);

        if (reservation == null) {
            return 0;
        }

        WorkSpace workSpace = workSpaceRepository.getWorkSpaceById(reservation.getSpaceId());

        if (workSpace != null) {
            workSpace.setAvailability(true);
            workSpaceRepository.updateWorkSpace(workSpace);
        }

        return reservationRepository.deleteReservation(reservationId);
    }
}
