package banquemisr.challenge05.TaskManagement.Task;

import banquemisr.challenge05.TaskManagement.User.User;
import banquemisr.challenge05.TaskManagement.User.UserDao;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class TaskSeeder {
    private final TaskDao taskDao;
    @SneakyThrows
    @PostConstruct
    @Transactional
    public void seedUsers() {
        if (taskDao.count() == 0) {
            taskDao.save(new Task("example","example for seeded task","todo",(short)3, LocalDate.now()));
            taskDao.save(new Task("example 2","second example for seeded task","todo",(short)3, LocalDate.now().plusDays(1)));
        }
    }
}
