package dev.blog.changuii.service;


import dev.blog.changuii.dto.TokenDTO;
import dev.blog.changuii.dto.UserDTO;
import dev.blog.changuii.exception.EmailDuplicationException;
import dev.blog.changuii.exception.EmailNullException;
import dev.blog.changuii.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    public UserDTO signup(UserDTO userDTO)
            throws EmailDuplicationException, EmailNullException;
    public TokenDTO signin(UserDTO userDTO)
            throws UserNotFoundException, EmailNullException;

}
