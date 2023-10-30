package dev.blog.changuii.service.impl;

import dev.blog.changuii.config.security.JwtProvider;
import dev.blog.changuii.dto.TokenDTO;
import dev.blog.changuii.dto.UserDTO;
import dev.blog.changuii.entity.UserEntity;
import dev.blog.changuii.repository.UserRepository;
import dev.blog.changuii.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthServiceImpl(
            @Autowired JwtProvider jwtProvider,
            @Autowired PasswordEncoder passwordEncoder,
            @Autowired UserRepository userRepository
    ){
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<UserDTO> signup(UserDTO userDTO) {

        UserEntity user;
        if(!(userDTO.getEmail() != null)){
            return ResponseEntity.status(400).body(UserDTO.builder().email("email null").build());
        }
        if(this.userRepository.existsByEmail(userDTO.getEmail())){
            return ResponseEntity.status(400).body(UserDTO.builder().email("duplicate email").build());
        }

        user = UserEntity.builder()
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        user = this.userRepository.save(user);
        return ResponseEntity.status(201).body(UserDTO.builder().email(user.getEmail()).build());
    }

    @Override
    public ResponseEntity<TokenDTO> signin(UserDTO userDTO) {
        if(!this.userRepository.existsByEmail(userDTO.getEmail()) || !(userDTO.getEmail() != null)){
            return ResponseEntity.status(400).body(TokenDTO.builder().email("email null").build());
        }

        UserEntity user = this.userRepository.findByEmail(userDTO.getEmail()).get();
        if(!this.passwordEncoder.matches(userDTO.getPassword(), user.getPassword())){
            return ResponseEntity.status(400).body(TokenDTO.builder().email("password mismatch").build());
        }


        return ResponseEntity.status(200).body(
                TokenDTO.builder()
                        .email(user.getEmail())
                        .token("Bearer "+this.jwtProvider.createToken(user.getEmail(), user.getRoles()))
                        .build()
        );
    }

}
