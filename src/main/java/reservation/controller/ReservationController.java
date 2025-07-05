package reservation.controller;

import reservation.entity.Reservation;
import reservation.entity.WorkSpace;
import jakarta.validation.Valid;
import reservation.model.IdModel;
import reservation.model.ReservationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reservation.service.ReservationService;
import reservation.service.WorkSpaceReservationService;
import reservation.service.WorkSpaceService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private WorkSpaceService workSpaceService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private WorkSpaceReservationService workSpaceReservationService;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    @GetMapping("/all")
    public List<ReservationModel> getAllReservations(Model model) {
        return reservationService.getAllReservations();
    }

    @PostMapping("/new")
    public ResponseEntity<?> createReservation(@RequestBody @Valid ReservationModel input) {
        LocalDate date;
        LocalTime timeStart;
        LocalTime timeEnd;
        Reservation newReservation;
        Optional<Reservation> reservation = reservationService.getReservationById(input.getId());

        if (reservation.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<WorkSpace> workSpace = workSpaceService.getWorkSpaceById(input.getSpaceId());

        if (workSpace.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            date = LocalDate.parse(input.getDate(), dateFormatter);
            timeStart = LocalTime.parse(input.getTimeStart(), timeFormatter);
            timeEnd = LocalTime.parse(input.getTimeEnd(), timeFormatter);
        } catch (DateTimeParseException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        newReservation = new Reservation(input.getId(), workSpace.get(), input.getClientName(), date, timeStart, timeEnd);
        workSpaceReservationService.addReservation(newReservation);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteReservation(@RequestBody @Valid IdModel input) {
        int status = workSpaceReservationService.deleteReservation(input.getId());

        if (status == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
