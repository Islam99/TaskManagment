package banquemisr.challenge05.TaskManagement.Task;

import banquemisr.challenge05.TaskManagement.History.History;
import banquemisr.challenge05.TaskManagement.History.HistoryService;
import banquemisr.challenge05.TaskManagement.User.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static banquemisr.challenge05.TaskManagement.Config.SystemUtil.*;


@AllArgsConstructor
@Service
public class TaskService {

    private final ObjectMapper mapper;
    private final TaskDao taskDao;
    private final HistoryService historyService;

    @Transactional
    public Task create(CreateTaskDTO taskDTO, User user) throws Exception
    {
        Task task = mapper.convertValue(taskDTO,Task.class);
        task = taskDao.save(task);
        historyService.createHistory(new History(user,task,"create",LocalDateTime.now()));
        return task;
    }

    @Transactional
    public Task update(UpdateTaskDTO taskDTO,Long id,User user) throws Exception
    {
        Task task = findById(id);
        if (Hibernate.isInitialized(task.getHistories())) {
            System.out.println("Tasks field is initialized.");
        } else {
            System.out.println("Tasks field is not initialized.");
        }
        Task data = mapper.convertValue(taskDTO,Task.class);
        task = (Task) setNonNullValues(data,task);
        task = taskDao.save(task);
        historyService.createHistory(new History(user,task,"update",LocalDateTime.now()));
        return task;
    }

    @Transactional
    public void delete(Long id) throws Exception
    {
        historyService.deleteByTaskId(id);
        taskDao.deleteById(id);
    }
    public Task findById(Long id)
    {
        return (Task) unwrapEntity(taskDao.findById(id),Task.class);
    }

    public Page<Task> findAll(Pageable pageable)
    {
        return taskDao.findAll(pageable);
    }

    public Page<Task> findAllFiltered(TaskFilter filter, Pageable pageable) {
        Specification<Task> spec = TaskSpec.filterBy(filter);
        return taskDao.findAll(spec, pageable);
    }

    public List<Task> findByDueDate(LocalDate dueDate)
    {
        return taskDao.findByDueDateAndStatusIsNotDone(dueDate);
    }

    public Page<History> findTaskHistory(Long id,Pageable pageable)
    {
        return historyService.findByTaskId(id,pageable);
    }

    public Page<Task> findAllSearchable(String searchString , Pageable pageable)
    {
        return taskDao.findTasksBySearchText(searchString , pageable);
    }
}
