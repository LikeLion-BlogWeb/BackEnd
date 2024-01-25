package dev.blog.changuii.service;


import dev.blog.changuii.dto.TokenDTO;
import dev.blog.changuii.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    public UserDTO signup(UserDTO userDTO);
    public TokenDTO signin(UserDTO userDTO);

}
