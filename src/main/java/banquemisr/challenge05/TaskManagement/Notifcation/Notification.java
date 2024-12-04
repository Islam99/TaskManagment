package banquemisr.challenge05.TaskManagement.Notifcation;


import banquemisr.challenge05.TaskManagement.User.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime timestamp;

    public Notification (String message, User user, LocalDateTime timestamp) {
        this.message = message;
        this.user = user;
        this.timestamp = timestamp;
    }
}