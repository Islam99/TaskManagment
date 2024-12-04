package banquemisr.challenge05.TaskManagement.Security;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/authenticate")
public class AuthenticateController {

    private final SecurityConstants securityConstants;
    //login
    @PostMapping
    public ResponseEntity<Void> setCookie(@Valid @RequestBody AuthDTO authRequest) {
        return ResponseEntity.ok().build();
    }


    //logout
    @DeleteMapping("/sign-out")
    public ResponseEntity<Void> deleteCookie(HttpServletResponse response, HttpServletRequest request) {
        Cookie[] cookies =  request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(securityConstants.getREFRESH_TOKEN())
                    || cookie.getName().equals(securityConstants.getAUTH_TOKEN()) ) {
                    cookie.setPath("/");
                    cookie.setHttpOnly(true);
                    cookie.setSecure(true);
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
        return ResponseEntity.ok().build();
    }

    @AllArgsConstructor
    @Getter
    @Setter
    @NoArgsConstructor
    public static class AuthDTO{
        @Schema(
                description = "email",
                example = "admin@gmail.com",
                required = true
        )
        @NotBlank(message = "email must be filled")
        private String email;
        @Schema(
                description = "password",
                example = "12345",
                required = true
        )
        @NotBlank(message = "password must be filled")
        private String password;
    }
}

