package banquemisr.challenge05.TaskManagement.User;

import banquemisr.challenge05.TaskManagement.Config.APIResponse;
import banquemisr.challenge05.TaskManagement.Notifcation.Notification;
import banquemisr.challenge05.TaskManagement.Notifcation.NotificationService;
import banquemisr.challenge05.TaskManagement.Security.LoggedInUser;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import static banquemisr.challenge05.TaskManagement.Config.SystemUtil.*;

@RequestMapping("api/user")
@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final NotificationService notificationService;
    @GetMapping("/notifications")

    public ResponseEntity<APIResponse<?>>
    findUserNotifications(@Schema(hidden = true)@LoggedInUser User user,
                          @RequestParam(required = false, defaultValue = "timestamp") String sort,
                          @RequestParam(required = false) String sortDirection,
                          @RequestParam(required = false, defaultValue = "0") Integer page,
                          @RequestParam(required = false, defaultValue = "10") Integer limit)
    {
        Pageable pageable = createPageable(sort,sortDirection,page,limit);
        APIResponse response = new APIResponse(null , notificationService.findByUser(user,pageable) , HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<?>> create(@Valid @RequestBody CreateUserDTO createUserDTO)
    {
        APIResponse response = new APIResponse(null , userService.create(createUserDTO) , HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/change-password")
    public ResponseEntity<APIResponse<?>> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO,
                                               @Schema(hidden = true)@LoggedInUser User user)
    {
        userService.changePassword(changePasswordDTO,user);
        APIResponse response = new APIResponse("Password changed successfully" , null , HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
