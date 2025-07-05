package reservation.repository;

import reservation.entity.WorkSpace;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class WorkSpaceRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public int addNewWorkspace(WorkSpace space) {
        entityManager.persist(space);
        return 1;
    }

    public WorkSpace getWorkSpaceById(int id) {
        return entityManager.find(WorkSpace.class, id);
    }

    public List<WorkSpace> getAllWorkSpaces() {
        return entityManager.createQuery("SELECT e FROM WorkSpace e", WorkSpace.class).getResultList();
    }

    public List<WorkSpace> getAllAvailableWorkSpaces() {
        return entityManager.createQuery("SELECT e FROM WorkSpace e WHERE e.availability = true", WorkSpace.class).getResultList();
    }

    @Transactional
    public int updateWorkSpace(WorkSpace space) {
        WorkSpace workSpace = getWorkSpaceById(space.getId());

        if (workSpace == null) {
            return 0;
        }

        entityManager.merge(space);
        entityManager.flush();

        return 1;
    }

    @Transactional
    public int deleteWorkspace(int id) {
        WorkSpace workSpace = getWorkSpaceById(id);

        if (workSpace != null) {
            entityManager.remove(workSpace);
            entityManager.flush();

            return 1;
        }

        return 0;
    }
}
