package reservation.controller;

import reservation.entity.Reservation;
import reservation.entity.WorkSpace;
import jakarta.validation.Valid;
import reservation.model.IdModel;
import reservation.model.ReservationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reservation.service.ReservationService;
import reservation.service.WorkSpaceReservationService;
import reservation.service.WorkSpaceService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private WorkSpaceService workSpaceService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private WorkSpaceReservationService workSpaceReservationService;

    @GetMapping
    public List<ReservationModel> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody @Valid ReservationModel input) {
        Optional<Reservation> reservation = reservationService.getReservationById(input.getId());

        if (reservation.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<WorkSpace> workSpace = workSpaceService.getWorkSpaceById(input.getSpaceId());

        if (workSpace.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (workSpaceReservationService.addReservation(input, workSpace.get()) == input.getId()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteReservation(@RequestBody @Valid IdModel input) {
        int status = workSpaceReservationService.deleteReservation(input.getId());

        if (status == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
