package dev.blog.changuii.exception;

public class EmailDuplicationException extends RuntimeException{

    public EmailDuplicationException() {
    }

    public EmailDuplicationException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
