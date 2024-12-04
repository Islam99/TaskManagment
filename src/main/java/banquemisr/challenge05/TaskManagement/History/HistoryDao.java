package banquemisr.challenge05.TaskManagement.History;

import banquemisr.challenge05.TaskManagement.Task.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryDao extends JpaRepository<History,Long> {

    @Modifying
    @Query("DELETE FROM History H WHERE H.task.id = :taskId")
    void deleteByTaskId(@Param("taskId") Long taskId);


    @Query("select H from History H where H.task.id = :taskId")
    Page<History> findByTaskId(@Param("taskId") Long taskId, Pageable pageable);
}
