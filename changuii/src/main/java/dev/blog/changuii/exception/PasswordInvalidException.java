package dev.blog.changuii.exception;

public class PasswordInvalidException extends RuntimeException{
    public PasswordInvalidException() {
    }

    public PasswordInvalidException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
