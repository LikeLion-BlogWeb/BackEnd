package dev.blog.changuii.exception;

public class EmailNullException extends RuntimeException{
    public EmailNullException() {
    }

    public EmailNullException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
