package banquemisr.challenge05.TaskManagement.Task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskDao extends JpaRepository<Task,Long>, JpaSpecificationExecutor<Task> {

    @EntityGraph(attributePaths = {"histories", "histories.user"})
    @Query("select t from Task t where t.dueDate = :dueDate and t.status <> 'done'")
    List<Task> findByDueDateAndStatusIsNotDone(LocalDate dueDate);

    @Query("SELECT t FROM Task t WHERE " +
            "LOWER(t.title) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(t.status) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(CAST(t.priority as text)) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(STR(t.dueDate)) LIKE LOWER(CONCAT('%', :searchText, '%')) " )
    Page<Task> findTasksBySearchText(@Param("searchText") String searchText , Pageable pageable);

}
