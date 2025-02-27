package exceptions;

public class ManagerSaveException extends RuntimeException {

    public ManagerSaveException(String msg) {
        super(msg);
    }

    public ManagerSaveException(Throwable cause) {
        super(cause);
    }

    public ManagerSaveException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
