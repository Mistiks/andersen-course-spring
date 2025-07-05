package reservation.controller;

import reservation.entity.WorkSpace;
import jakarta.validation.Valid;
import reservation.model.IdModel;
import reservation.model.WorkSpaceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reservation.service.WorkSpaceReservationService;
import reservation.service.WorkSpaceService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/workspace")
public class WorkspaceController {

    @Autowired
    private WorkSpaceService workSpaceService;

    @Autowired
    private WorkSpaceReservationService workSpaceReservationService;

    @PostMapping("/new")
    public ResponseEntity<?> createNewSpace(@RequestBody @Valid WorkSpaceModel input) {
        Optional<WorkSpace> workSpace = workSpaceService.getWorkSpaceById(input.getId());

        if (workSpace.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        workSpaceService.addNewWorkspace(new WorkSpace(input.getId(), input.getType(), input.getPrice()));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateSpace(@RequestBody @Valid WorkSpaceModel input) {
        Optional<WorkSpace> workSpace = workSpaceService.getWorkSpaceById(input.getId());
        WorkSpace space;

        if (workSpace.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        space = workSpace.get();
        space.setType(input.getType());
        space.setPrice(input.getPrice());
        space.setAvailability(input.isAvailability());
        workSpaceService.updateWorkSpace(space);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteSpace(@RequestBody @Valid IdModel input) {
        Optional<WorkSpace> workSpace = workSpaceService.getWorkSpaceById(input.getId());

        if (workSpace.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        workSpaceReservationService.deleteWorkSpace(workSpace.get().getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<WorkSpaceModel> getAllSpaces() {
        return workSpaceService.getAllWorkSpacesInfo();
    }

    @GetMapping("/available")
    public List<WorkSpaceModel> getAvailableSpace() {
        return workSpaceService.getAllAvailableWorkSpaces();
    }
}
