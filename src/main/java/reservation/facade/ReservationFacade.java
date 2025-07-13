package reservation.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reservation.entity.Reservation;
import reservation.entity.WorkSpace;
import reservation.model.IdModel;
import reservation.model.ReservationModel;
import reservation.service.ReservationService;
import reservation.service.WorkSpaceReservationService;
import reservation.service.WorkSpaceService;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationFacade {

    private final WorkSpaceService workSpaceService;
    private final ReservationService reservationService;
    private final WorkSpaceReservationService workSpaceReservationService;

    @Autowired
    public ReservationFacade(WorkSpaceService workSpaceService, ReservationService reservationService,
                             WorkSpaceReservationService workSpaceReservationService) {
        this.workSpaceService = workSpaceService;
        this.reservationService = reservationService;
        this.workSpaceReservationService = workSpaceReservationService;
    }

    public List<ReservationModel> getAllReservations() {
        return reservationService.getAllReservations();
    }

    public int createReservation(ReservationModel input) {
        Optional<Reservation> reservation = reservationService.getReservationById(input.getId());

        if (reservation.isPresent()) {
            return 0;
        }

        Optional<WorkSpace> workSpace = workSpaceService.getWorkSpaceById(input.getSpaceId());

        if (workSpace.isEmpty()) {
            return 0;
        }

        if (workSpaceReservationService.addReservation(input, workSpace.get()) == input.getId()) {
            return 1;
        } else {
            return 0;
        }
    }

    public int deleteReservation(IdModel input) {
        return workSpaceReservationService.deleteReservation(input.getId());
    }
}
