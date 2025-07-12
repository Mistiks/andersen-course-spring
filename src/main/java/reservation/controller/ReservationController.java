package reservation.controller;

import jakarta.validation.Valid;
import reservation.model.IdModel;
import reservation.model.ReservationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reservation.service.ReservationFacadeService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationFacadeService reservationFacadeService;

    @GetMapping
    public List<ReservationModel> getAllReservations() {
        return reservationFacadeService.getAllReservations();
    }

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody @Valid ReservationModel input) {
        int status = reservationFacadeService.createReservation(input);

        if (status == 1) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteReservation(@RequestBody @Valid IdModel input) {
        int status = reservationFacadeService.deleteReservation(input);

        if (status == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
