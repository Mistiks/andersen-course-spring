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
@RequestMapping("/workspaces")
public class WorkspaceController {

    @Autowired
    private WorkSpaceService workSpaceService;

    @Autowired
    private WorkSpaceReservationService workSpaceReservationService;

    @PostMapping
    public ResponseEntity<?> createNewSpace(@RequestBody @Valid WorkSpaceModel input) {
        Optional<WorkSpace> workSpace = workSpaceService.getWorkSpaceById(input.getId());

        if (workSpace.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (workSpaceService.addNewWorkspace(input) == input.getId()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<?> updateSpace(@RequestBody @Valid WorkSpaceModel input) {
        Optional<WorkSpace> workSpace = workSpaceService.getWorkSpaceById(input.getId());

        if (workSpace.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (workSpaceService.updateWorkSpace(input) == input.getId()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteSpace(@RequestBody @Valid IdModel input) {
        Optional<WorkSpace> workSpace = workSpaceService.getWorkSpaceById(input.getId());

        if (workSpace.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
