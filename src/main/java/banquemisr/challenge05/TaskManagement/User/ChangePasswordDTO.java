package banquemisr.challenge05.TaskManagement.User;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChangePasswordDTO {

    @NotBlank
    public String oldPassword;
    @NotBlank
    public String password;
    @NotBlank
    public String confirmedPassword;
}
