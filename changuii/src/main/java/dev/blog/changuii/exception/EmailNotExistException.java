package dev.blog.changuii.exception;

public class EmailNotExistException extends RuntimeException{
    public EmailNotExistException() {
        super("존재하지 않는 이메일입니다.");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
