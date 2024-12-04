package banquemisr.challenge05.TaskManagement.Exception;

public class CustomException extends RuntimeException {
    private final int code;

    public CustomException(CustomError errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
