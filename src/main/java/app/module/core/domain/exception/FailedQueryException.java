package app.module.core.domain.exception;

public class FailedQueryException extends Exception {
    public FailedQueryException(String message, Throwable cause) {
        super(message, cause);
    }
}
