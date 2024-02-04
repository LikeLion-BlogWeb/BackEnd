package dev.blog.changuii.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException() {
        super("유저를 찾을 수 없습니다.");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
