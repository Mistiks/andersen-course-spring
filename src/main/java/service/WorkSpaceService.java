package service;

import entity.WorkSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.WorkSpaceRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<String> getAllWorkSpacesInfo() {
        List<WorkSpace> workSpaceList = workSpaceRepository.getAllWorkSpaces();

        return workSpaceList.stream().map(WorkSpace::toString).toList();
    }

    public List<String> getAllAvailableWorkSpaces() {
        return workSpaceRepository.getAllAvailableWorkSpaces().stream().map(WorkSpace::toString).toList();
    }

    public int updateWorkSpace(WorkSpace space) {
        return workSpaceRepository.updateWorkSpace(space);
    }
}
