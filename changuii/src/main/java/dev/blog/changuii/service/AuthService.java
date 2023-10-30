package dev.blog.changuii.service;


import dev.blog.changuii.dto.TokenDTO;
import dev.blog.changuii.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    public ResponseEntity<UserDTO> signup(UserDTO userDTO);
    public ResponseEntity<TokenDTO> signin(UserDTO userDTO);

}
