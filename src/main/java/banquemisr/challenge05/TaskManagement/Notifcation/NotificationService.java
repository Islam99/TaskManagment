package banquemisr.challenge05.TaskManagement.Notifcation;

import banquemisr.challenge05.TaskManagement.Task.Task;
import banquemisr.challenge05.TaskManagement.User.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {
    private final NotificationDao notificationDao;
    public Notification create(Notification notification)
    {
        return notificationDao.save(notification);
    }

    public Page<Notification> findByUser(User user,Pageable pageable)
    {
        return notificationDao.findByUser(user,pageable);
    }

    public void deleteByTimestampBefore(LocalDateTime timestamp)
    {
         notificationDao.deleteByTimestampBefore(timestamp);
    }
}
