package banquemisr.challenge05.TaskManagement.Task;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateTaskDTO
{
    @Size(min = 4, max = 30, message = "Title must be in between 4 and 30 characters")
    private String title;

    @Size(min = 5, max = 100, message = "Description must be in between 5 and 100 characters")
    private String description;

    @Pattern(regexp = "todo|inprogress|done", message = "Status must be one of: active, inactive, pending")
    private String status;

    @Min(value = 1, message = "Value must be greater than or equal to 1.")
    @Max(value = 3, message = "Value must be less than or equal to 3.")
    private Short priority;

    @FutureOrPresent(message = "The date must be today or later")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
}
