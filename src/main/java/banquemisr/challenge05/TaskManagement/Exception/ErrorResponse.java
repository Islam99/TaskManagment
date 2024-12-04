package banquemisr.challenge05.TaskManagement.Exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter@Getter@AllArgsConstructor@NoArgsConstructor
public class ErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private List<String> messages;
    private int code;

    public ErrorResponse(List<String> messages, int code) {
        this.timestamp = LocalDateTime.now();
        this.messages = messages;
        this.code =code;
    }
}

