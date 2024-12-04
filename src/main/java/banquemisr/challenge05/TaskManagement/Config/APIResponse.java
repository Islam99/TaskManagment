package banquemisr.challenge05.TaskManagement.Config;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class APIResponse<T> {

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    private HttpStatus status;

    public APIResponse(String message, HttpStatus status) {
        this.message = message;
        this.status =status;
    }
}