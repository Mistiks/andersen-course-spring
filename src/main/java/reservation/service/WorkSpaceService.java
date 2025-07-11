package reservation.service;

import org.springframework.transaction.annotation.Transactional;
import reservation.entity.WorkSpace;
import reservation.model.WorkSpaceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reservation.repository.WorkSpaceRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class WorkSpaceService {

    private final WorkSpaceRepository workSpaceRepository;

    @Autowired
    public WorkSpaceService(WorkSpaceRepository workSpaceRepository) {
        this.workSpaceRepository = workSpaceRepository;
    }

    @Transactional
    public int addNewWorkspace(WorkSpaceModel space) {
        WorkSpace newSpace = new WorkSpace(space);

        return workSpaceRepository.save(newSpace).getId();
    }

    public Optional<WorkSpace> getWorkSpaceById(int spaceId) {
        return workSpaceRepository.findById(spaceId);
    }

    public List<WorkSpaceModel> getAllWorkSpacesInfo() {
        return workSpaceRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(WorkSpace::getId))
                .map(WorkSpaceModel::new)
                .toList();
    }

    public List<WorkSpaceModel> getAllAvailableWorkSpaces() {
        return workSpaceRepository.findByAvailabilityTrue().stream()
                .map(WorkSpaceModel::new)
                .toList();
    }

    @Transactional
    public int updateWorkSpace(WorkSpaceModel space) {
        WorkSpace newSpace = new WorkSpace(space);

        return workSpaceRepository.save(newSpace).getId();
    }
}
