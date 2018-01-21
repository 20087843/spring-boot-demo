package pri.smilly.demo.exception;

public class ProcessExeException extends Exception {
    public ProcessExeException() {
    }

    public ProcessExeException(String message) {
        super(message);
    }

    public ProcessExeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessExeException(Throwable cause) {
        super(cause);
    }

    public ProcessExeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
