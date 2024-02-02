package dev.blog.changuii.advisor;

import dev.blog.changuii.exception.PostNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ExceptionAdvisor {

    // MethodArgumentNotValidException, 유효성 처리 발생시 던져지는 Exception을 핸들링
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> validationHandler(MethodArgumentNotValidException e){
        List<FieldError> errors = e.getBindingResult().getFieldErrors();

        StringBuilder sb = new StringBuilder();
        for(FieldError error : errors ){
            sb
                    .append(error.getDefaultMessage())
                    .append("\n")
                    .append("입력된 값 : ")
                    .append(error.getRejectedValue())
                    .append("\n");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sb.toString());
    }


    // PostController에서  PostService호출시 발생하는 예외에 대한 핸들링
    @ExceptionHandler({PostNotFoundException.class})
    public ResponseEntity<String> postControllerExceptionHandler(
            Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

    }


    // AuthController에서 AuthService 호출 시 발생하는 예외에 대한 핸들링
    @ExceptionHandler()
    public ResponseEntity<String> authControllerExceptionHandler(
            Exception e
    ){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }




}
