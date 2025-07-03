package controller;

import entity.WorkSpace;
import jakarta.validation.Valid;
import model.IdModel;
import model.WorkSpaceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import service.ReservationService;
import service.WorkSpaceReservationService;
import service.WorkSpaceService;

import java.util.Optional;

@Controller
public class AdminController {

    private final WorkSpaceService workSpaceService;
    private final ReservationService reservationService;
    private final WorkSpaceReservationService workSpaceReservationService;

    @Autowired
    public AdminController(WorkSpaceService workSpaceService, ReservationService reservationService,
                           WorkSpaceReservationService workSpaceReservationService) {
        this.workSpaceService = workSpaceService;
        this.reservationService = reservationService;
        this.workSpaceReservationService = workSpaceReservationService;
    }

    @GetMapping("/admin")
    public String showMenu(Model model) {
        return "admin/adminMenu";
    }

    @GetMapping("/space/new")
    public String showNewSpaceForm(Model model) {
        model.addAttribute("spaceInput", new WorkSpaceModel());
        return "admin/newSpace";
    }

    @PostMapping("/space/new")
    public String processNewSpaceForm(@ModelAttribute("spaceInput") @Valid WorkSpaceModel input,
                                      BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/newSpace";
        }

        Optional<WorkSpace> workSpace = workSpaceService.getWorkSpaceById(input.getId());

        if (workSpace.isPresent()) {
            model.addAttribute("error", "Workspace with id " + input.getId() + " already exists!");
            return "admin/newSpace";
        }

        workSpaceService.addNewWorkspace(new WorkSpace(input.getId(), input.getType(), input.getPrice()));

        return "admin/adminMenu";
    }

    @GetMapping("/space/update")
    public String showUpdateSpaceForm(Model model) {
        model.addAttribute("spaceInput", new WorkSpaceModel());
        return "admin/updateSpace";
    }

    @PostMapping("/space/update")
    public String processUpdateSpaceForm(@ModelAttribute("spaceInput") @Valid WorkSpaceModel input,
                                      BindingResult result, Model model) {
        Optional<WorkSpace> workSpace;
        WorkSpace space;

        if (result.hasErrors()) {
            return "admin/updateSpace";
        }

        workSpace = workSpaceService.getWorkSpaceById(input.getId());

        if (workSpace.isEmpty()) {
            model.addAttribute("error", "Workspace with id " + input.getId() + " doesn't exist!");
            return "admin/updateSpace";
        }

        space = workSpace.get();
        space.setType(input.getType());
        space.setPrice(input.getPrice());
        space.setAvailability(input.isAvailability());
        workSpaceService.updateWorkSpace(space);

        return "admin/adminMenu";
    }

    @GetMapping("/space/delete")
    public String showDeleteSpaceForm(Model model) {
        model.addAttribute("idInput", new IdModel());
        return "admin/deleteSpace";
    }

    @PostMapping("/space/delete")
    public String processDeleteSpaceForm(@ModelAttribute("idInput") @Valid IdModel input,
                                         BindingResult result, Model model) {
        Optional<WorkSpace> workSpace;

        if (result.hasErrors()) {
            return "admin/deleteSpace";
        }

        workSpace = workSpaceService.getWorkSpaceById(input.getId());

        if (workSpace.isEmpty()) {
            model.addAttribute("error", "Workspace with id " + input.getId() + " doesn't exist!");
            return "admin/deleteSpace";
        }

        workSpaceReservationService.deleteWorkSpace(workSpace.get().getId());

        return "admin/adminMenu";
    }

    @GetMapping("/space/all")
    public String showWorkspaces(Model model) {
        model.addAttribute("workspaceList", workSpaceService.getAllWorkSpacesInfo());
        return "admin/allWorkSpaces";
    }

    @GetMapping("/reservation/all")
    public String showReservations(Model model) {
        model.addAttribute("reservationList", reservationService.getAllReservations());
        return "admin/allReservations";
    }
}
