package banquemisr.challenge05.TaskManagement.History;


import banquemisr.challenge05.TaskManagement.Task.Task;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class HistoryService {
    private final HistoryDao historyDao;

    public History createHistory(History history)
    {
           return historyDao.save(history);
    }

    public Page<History> findByTaskId(Long taskId, Pageable pageable)
    {
        return historyDao.findByTaskId(taskId,pageable);
    }

    public void deleteByTaskId(Long taskId)
    {
         historyDao.deleteByTaskId(taskId);
    }

}
