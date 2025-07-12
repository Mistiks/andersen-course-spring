package reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reservation.entity.Reservation;
import reservation.entity.WorkSpace;
import reservation.model.IdModel;
import reservation.model.ReservationModel;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationFacadeService {

    @Autowired
    private WorkSpaceService workSpaceService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private WorkSpaceReservationService workSpaceReservationService;

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
