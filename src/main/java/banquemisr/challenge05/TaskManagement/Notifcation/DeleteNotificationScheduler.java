package banquemisr.challenge05.TaskManagement.Notifcation;

import banquemisr.challenge05.TaskManagement.Task.Task;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@AllArgsConstructor
@Component
public class DeleteNotificationScheduler {

    private final NotificationService notificationService;
    @Scheduled(cron = "0 0/30 12 * * *")
    @Transactional
    public void deleteOldNotifications() {
        notificationService.deleteByTimestampBefore(LocalDateTime.now().minusWeeks(1));
    }
}
