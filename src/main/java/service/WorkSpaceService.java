package service;

import entity.WorkSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.WorkSpaceRepository;

import java.util.Comparator;
import java.util.List;
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

    public String getAllWorkSpaces() {
        List<WorkSpace> workSpaceList = workSpaceRepository.getAllWorkSpaces();

        String workspacesData = workSpaceList
                .stream()
                .sorted(Comparator.comparing(WorkSpace::getId))
                .map(WorkSpace::toString)
                .collect(Collectors.joining("\n", "", "\n"));

        if (workspacesData.isEmpty()) {
            return "Workspaces not found!\n";
        }

        return workspacesData;
    }

    public String getAllAvailableWorkSpaces() {
        List<WorkSpace> workSpaceList = workSpaceRepository.getAllAvailableWorkSpaces();
        String workspacesData = workSpaceList
                .stream()
                .sorted(Comparator.comparing(WorkSpace::getId))
                .map(WorkSpace::toString)
                .collect(Collectors.joining("\n", "", "\n"));

        if (workspacesData.isEmpty()) {
            return "Workspaces not found!\n";
        }

        return workspacesData;
    }

    public int updateWorkSpace(WorkSpace space) {
        return workSpaceRepository.updateWorkSpace(space);
    }
}
