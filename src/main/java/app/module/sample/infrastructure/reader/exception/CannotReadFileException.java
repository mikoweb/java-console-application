package app.module.sample.infrastructure.reader.exception;

public class CannotReadFileException extends Exception {
    public CannotReadFileException(String message) {
        super(message);
    }
}
