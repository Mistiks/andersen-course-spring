package controller;

import entity.Reservation;
import entity.WorkSpace;
import jakarta.validation.Valid;
import model.IdModel;
import model.ReservationModel;
import model.WorkSpaceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import service.ReservationService;
import service.WorkSpaceReservationService;
import service.WorkSpaceService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Controller
public class UserController {

    private final WorkSpaceService workSpaceService;
    private final ReservationService reservationService;
    private final WorkSpaceReservationService workSpaceReservationService;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    @Autowired
    public UserController(WorkSpaceService workSpaceService, ReservationService reservationService,
                           WorkSpaceReservationService workSpaceReservationService) {
        this.workSpaceService = workSpaceService;
        this.reservationService = reservationService;
        this.workSpaceReservationService = workSpaceReservationService;
    }

    @GetMapping("/user")
    public String showMenu(Model model) {
        return "user/userMenu";
    }

    @GetMapping("/space/available")
    public String showAvailableSpaceForm(Model model) {
        model.addAttribute("workspaceList", workSpaceService.getAllAvailableWorkSpaces());
        return "user/availableSpace";
    }

    @GetMapping("/reservation/my")
    public String showReservations(Model model) {
        model.addAttribute("reservationList", reservationService.getAllReservations());
        return "user/myReservations";
    }

    @GetMapping("/reservation/new")
    public String showNewReservationForm(Model model) {
        model.addAttribute("reservationInput", new ReservationModel());
        return "user/newReservation";
    }

    @PostMapping("/reservation/new")
    public String processNewReservationForm(@ModelAttribute("spaceInput") @Valid ReservationModel input,
                                      BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/newReservation";
        }

        LocalDate date;
        LocalTime timeStart;
        LocalTime timeEnd;
        Reservation newReservation;
        Optional<Reservation> reservation = reservationService.getReservationById(input.getId());

        if (reservation.isPresent()) {
            model.addAttribute("error", "Reservation with id " + input.getId() + " already exists!");
            return "user/newReservation";
        }

        Optional<WorkSpace> workSpace = workSpaceService.getWorkSpaceById(input.getSpaceId());

        if (workSpace.isEmpty()) {
            model.addAttribute("error", "Workspace with id " + input.getId() + " doesn't exist!");
            return "user/newReservation";
        }

        try {
            date = LocalDate.parse(input.getDate(), dateFormatter);
            timeStart = LocalTime.parse(input.getTimeStart(), timeFormatter);
            timeEnd = LocalTime.parse(input.getTimeEnd(), timeFormatter);
        } catch (DateTimeParseException exception) {
            model.addAttribute("error", "Incorrect date or time input!");
            return "user/newReservation";
        }

        newReservation = new Reservation(input.getId(), workSpace.get(), input.getClientName(), date, timeStart, timeEnd);
        workSpaceReservationService.addReservation(newReservation);

        return "user/userMenu";
    }

    @GetMapping("/reservation/delete")
    public String showDeleteReservationForm(Model model) {
        model.addAttribute("idInput", new IdModel());
        return "user/deleteReservation";
    }

    @PostMapping("/reservation/delete")
    public String processDeleteReservationForm(@ModelAttribute("idInput") @Valid IdModel input,
                                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/deleteReservation";
        }

        int status = workSpaceReservationService.deleteReservation(input.getId());

        if (status == 0) {
            model.addAttribute("error", "Reservation with id " + input.getId() + " doesn't exist!");
            return "user/deleteReservation";
        }

        return "user/userMenu";
    }
}
