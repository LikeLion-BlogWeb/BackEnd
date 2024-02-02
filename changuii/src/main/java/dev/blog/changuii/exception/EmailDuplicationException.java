package dev.blog.changuii.exception;

public class EmailDuplicationException extends RuntimeException{

    public EmailDuplicationException() {
        super("이미 존재하는 이메일입니다.");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
