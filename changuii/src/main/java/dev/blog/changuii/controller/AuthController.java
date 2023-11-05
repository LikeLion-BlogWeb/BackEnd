package dev.blog.changuii.controller;


import dev.blog.changuii.dto.TokenDTO;
import dev.blog.changuii.dto.UserDTO;
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

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
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
        logger.info("[SignUp] "+userDTO.toString());
        return this.authService.signup(userDTO);
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenDTO> signin(
            @RequestBody UserDTO userDTO
    ){
        logger.info("[SingIn] "+userDTO.toString());
        return this.authService.signin(userDTO);
    }

}
