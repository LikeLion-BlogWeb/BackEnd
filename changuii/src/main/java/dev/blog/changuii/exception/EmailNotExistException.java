package dev.blog.changuii.exception;

public class EmailNotExistException extends RuntimeException{
    public EmailNotExistException() {
    }

    public EmailNotExistException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
