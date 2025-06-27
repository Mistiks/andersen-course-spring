package service;

import entity.WorkSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.WorkSpaceRepository;

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

    public Optional<WorkSpace> getWorkSpaceById(int id) {
        return workSpaceRepository.getWorkSpaceById(id);
    }

    public List<WorkSpace> getAllWorkSpaces() {
        return workSpaceRepository.getAllWorkSpaces();
    }

    public List<WorkSpace> getAllAvailableWorkSpaces() {
        return workSpaceRepository.getAllAvailableWorkSpaces();
    }

    public int updateWorkSpace(WorkSpace space) {
        return workSpaceRepository.updateWorkSpace(space);
    }

    public int deleteWorkspace(int id) {
        return workSpaceRepository.deleteWorkspace(id);
    }
}
