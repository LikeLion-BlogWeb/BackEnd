package dev.blog.changuii.service.impl;

import dev.blog.changuii.config.security.JwtProvider;
import dev.blog.changuii.dao.UserDAO;
import dev.blog.changuii.dto.TokenDTO;
import dev.blog.changuii.dto.UserDTO;
import dev.blog.changuii.entity.UserEntity;
import dev.blog.changuii.exception.EmailDuplicationException;
import dev.blog.changuii.exception.EmailNotExistException;
import dev.blog.changuii.exception.EmailNullException;
import dev.blog.changuii.exception.PasswordInvalidException;
import dev.blog.changuii.repository.UserRepository;
import dev.blog.changuii.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthServiceImpl(
            @Autowired JwtProvider jwtProvider,
            @Autowired PasswordEncoder passwordEncoder,
            @Autowired UserDAO userDAO
    ){
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.userDAO = userDAO;
    }

    private void checkEmail(String email, boolean flag, String message){
        // Duplication check
        if(flag){
            if(this.userDAO.existByEmail(email))
                throw new EmailDuplicationException(message);
        }
        // Email Exists check
        else{
            if(!this.userDAO.existByEmail(email))
                throw new EmailNotExistException(message);
        }

    }

    @Override
    public UserDTO signup(UserDTO userDTO) throws EmailDuplicationException, EmailNullException{

        UserEntity user;
        this.checkEmail(userDTO.getEmail(), true, userDTO.getEmail()+"은 이미 회원가입된 이메일입니다.");

        try {
            userDTO.setPassword(this.passwordEncoder.encode(userDTO.getPassword()));
            user = UserEntity.initUserEntity(userDTO);

            user = this.userDAO.createUser(user);
            return UserDTO.builder().email(user.getEmail()).build();
        }
        catch (NullPointerException | IllegalArgumentException e) {
            e.printStackTrace();
            throw new EmailNullException("잘못된 요청입니다.");
        }
    }

    @Override
    public TokenDTO signin(UserDTO userDTO) {

        this.checkEmail(userDTO.getEmail(), false, "회원가입되지 않은 이메일입니다.");

        try {
            UserEntity user = this.userDAO.readUser(userDTO.getEmail());
            if (!this.passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
                throw new PasswordInvalidException("비밀번호가 일치하지 않습니다.");
            }

            return TokenDTO.makeTokenDTO(user.getEmail(), "Bearer " + this.jwtProvider.createToken(user.getEmail(), user.getRoles()));
        }catch (NullPointerException e){
            e.printStackTrace();
            throw new EmailNullException("잘못된 요청입니다.");
        }
    }

}
