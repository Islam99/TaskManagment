package banquemisr.challenge05.TaskManagement.Mailer;


import banquemisr.challenge05.TaskManagement.History.History;
import banquemisr.challenge05.TaskManagement.Notifcation.Notification;
import banquemisr.challenge05.TaskManagement.Notifcation.NotificationService;
import banquemisr.challenge05.TaskManagement.Task.Task;
import banquemisr.challenge05.TaskManagement.Task.TaskService;
import banquemisr.challenge05.TaskManagement.User.User;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor

@Component
public class MailScheduler {
    private final TaskService taskService;
    private final EmailService emailService;
    private final NotificationService notificationService;
    private final EntityManager entityManager;
    @Scheduled(cron = "0 0/30 12 * * *")
    @Transactional
    public void notifyDueTasks() {
        System.out.println("sendMail");
        List<Task> todayTasks = taskService.findByDueDate(LocalDate.now());
        for (Task task : todayTasks) {
            sendMailToExtractedUser(task);
        }
    }

    public void sendMailToExtractedUser(Task task)
    {
        try {
            Set<User> users = task.getHistories().stream().map(History::getUser).collect(Collectors.toSet());
            String message = "Your task with title " + task.getTitle() + "is due today please provide your contribution";
            for (User user : users) {
                System.out.println(user.toString());
                entityManager.persist(user);
                notificationService.create(new Notification(message , user , LocalDateTime.now()));
                emailService.sendEmail(user.getEmail(), "Task is due", message, "taskManagement@gmal.com");
            }
        }
        catch (Exception e)
        {System.out.println(e.getMessage());}

    }
}
