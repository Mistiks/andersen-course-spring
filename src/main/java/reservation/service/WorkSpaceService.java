package reservation.service;

import reservation.entity.WorkSpace;
import reservation.model.WorkSpaceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reservation.repository.WorkSpaceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkSpaceService {

    private final WorkSpaceRepository workSpaceRepository;

    @Autowired
    public WorkSpaceService(WorkSpaceRepository workSpaceRepository) {
        this.workSpaceRepository = workSpaceRepository;
    }

    public int addNewWorkspace(WorkSpace space) {
        return workSpaceRepository.addNewWorkspace(space);
    }

    public Optional<WorkSpace> getWorkSpaceById(int spaceId) {
        return Optional.ofNullable(workSpaceRepository.getWorkSpaceById(spaceId));
    }

    public List<WorkSpaceModel> getAllWorkSpacesInfo() {
        List<WorkSpace> workSpaceList = workSpaceRepository.getAllWorkSpaces();

        return workSpaceList.stream()
                .map(i -> new WorkSpaceModel(i.getId(), i.getType(), i.getPrice(), i.getAvailability()))
                .toList();
    }

    public List<WorkSpaceModel> getAllAvailableWorkSpaces() {
        return workSpaceRepository.getAllAvailableWorkSpaces().stream()
                .map(i -> new WorkSpaceModel(i.getId(), i.getType(), i.getPrice(), i.getAvailability()))
                .toList();
    }

    public int updateWorkSpace(WorkSpace space) {
        return workSpaceRepository.updateWorkSpace(space);
    }
}
