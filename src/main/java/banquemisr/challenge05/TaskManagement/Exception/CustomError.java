package banquemisr.challenge05.TaskManagement.Exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

public enum CustomError {
    Entity_NOT_FOUND(1001, "Entity not found"),
    VALIDATION_ERROR(1002, "Validation failed"),
    UNAUTHORIZED_ACCESS(1003, "Unauthorized access"),
    USER_IS_LOCKED(1004, "User is locked"),
    USER_IS_ALREADY_VERIFIED(1005, "User is already verified"),
    VERIFICATION_CODE_ALREADY_SENT(1006, "User must wait to send verification code"),
    VERIFICATION_FAILED(1007, "Verification code is incorrect"),
    VERIFICATION_ATTEMPTS_EXCEEDED(1008, "Verification attempts exceeded"),
    INVALID_PASSWORD(1009,"Password is not correct " ),
    USER_ALREADY_EXISTS(1010,"User with email already exists" ),
    PASSWORD_DOESNT_MATCH(1011,"Password doesn't match" );

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final int code;
    private final String message;

    CustomError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}