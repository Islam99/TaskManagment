package banquemisr.challenge05.TaskManagement.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CreateUserDTO {
    @Email@NotBlank
    String email;
    @NotBlank
    String password;
    @Pattern(regexp = "ADMIN|USER", message = "Only admin and user types can be added")
    String role;
}
