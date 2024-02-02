package dev.blog.changuii.exception;

public class PasswordInvalidException extends RuntimeException{
    public PasswordInvalidException() {
        super("password가 유효하지 않습니다.");
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
