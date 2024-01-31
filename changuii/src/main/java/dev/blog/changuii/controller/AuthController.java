package dev.blog.changuii.controller;


import dev.blog.changuii.dto.TokenDTO;
import dev.blog.changuii.dto.UserDTO;
import dev.blog.changuii.exception.EmailDuplicationException;
import dev.blog.changuii.exception.EmailNotExistException;
import dev.blog.changuii.exception.EmailNullException;
import dev.blog.changuii.exception.PasswordInvalidException;
import dev.blog.changuii.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(
            @Autowired AuthService authService
    ){
        this.authService = authService;
    }


    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(
            @RequestBody UserDTO userDTO
    ){
        try {
            return ResponseEntity.status(201).body(this.authService.signup(userDTO));
        }catch (EmailDuplicationException | EmailNullException e){
            return ResponseEntity.status(400).body(UserDTO.builder().email(e.getMessage()).build());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenDTO> signin(
            @RequestBody UserDTO userDTO
    ){
        try{
            return ResponseEntity.status(200).body(this.authService.signin(userDTO));
        }catch (EmailNotExistException | PasswordInvalidException e){
            return ResponseEntity.status(400).body(TokenDTO.builder().email(e.getMessage()).build());
        }
    }

}
