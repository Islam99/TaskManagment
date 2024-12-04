package banquemisr.challenge05.TaskManagement.Notifcation;

import banquemisr.challenge05.TaskManagement.User.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationDao extends JpaRepository<Notification,Long> {
    public Page<Notification> findByUser(User user, Pageable pageable);
    public void deleteByTimestampBefore(LocalDateTime localDateTime);
}
