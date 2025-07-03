package repository;

import entity.WorkSpace;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class WorkSpaceRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public int addNewWorkspace(WorkSpace space) {
        entityManager.persist(space);
        return 1;
    }

    public Optional<WorkSpace> getWorkSpaceById(int id) {
        WorkSpace workSpace = entityManager.find(WorkSpace.class, id);

        if (workSpace != null) {
            return Optional.of(workSpace);
        } else {
            return Optional.empty();
        }
    }

    public List<WorkSpace> getAllWorkSpaces() {
        return entityManager.createQuery("SELECT e FROM WorkSpace e", WorkSpace.class).getResultList();
    }

    public List<WorkSpace> getAllAvailableWorkSpaces() {
        return entityManager.createQuery("SELECT e FROM WorkSpace e WHERE e.availability = true", WorkSpace.class).getResultList();
    }

    @Transactional
    public int updateWorkSpace(WorkSpace space) {
        Optional<WorkSpace> workSpace = getWorkSpaceById(space.getId());

        if (workSpace.isEmpty()) {
            return 0;
        }

        entityManager.merge(space);
        entityManager.flush();

        return 1;
    }

    @Transactional
    public int deleteWorkspace(int id) {
        Optional<WorkSpace> workSpace = getWorkSpaceById(id);

        if (workSpace.isPresent()) {
            entityManager.remove(workSpace.get());
            entityManager.flush();

            return 1;
        }

        return 0;
    }
}
