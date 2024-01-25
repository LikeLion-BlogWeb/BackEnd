package dev.blog.changuii.service;


import dev.blog.changuii.dao.UserDAO;
import dev.blog.changuii.dto.TokenDTO;
import dev.blog.changuii.dto.UserDTO;
import dev.blog.changuii.entity.UserEntity;
import dev.blog.changuii.exception.EmailDuplicationException;
import dev.blog.changuii.exception.EmailNotExistException;
import dev.blog.changuii.exception.EmailNullException;
import dev.blog.changuii.exception.PasswordInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class AuthServiceTest {


    private final AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceTest.class);
    @MockBean
    private UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private final UserDTO userDTO = new UserDTO();

    public AuthServiceTest(
            @Autowired AuthService authService,
            @Autowired PasswordEncoder passwordEncoder
    ){
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    @BeforeEach
    public void setUp(){
        userDTO.setEmail("asd123@naver.com");
        userDTO.setPassword("1234");
        logger.info("UserDTO 초기화 완료 : "+ userDTO);
    }

    @Test
    @DisplayName("회원가입")
    public void signUpTest(){
        // given
        UserEntity userEntity = UserEntity.initUserEntity(userDTO);

        when(this.userDAO.createUser(any())).thenReturn(userEntity);
        when(this.userDAO.existByEmail(userEntity.getEmail())).thenReturn(false);

        // when
        UserDTO after = this.authService.signup(this.userDTO);

        // then
        this.checkUserDTO(userDTO, after);
    }

    @Test
    @DisplayName("Null 회원가입")
    public void nullDataSignUpTest(){
        when(this.userDAO.existByEmail(null)).thenReturn(false);
        when(this.userDAO.createUser(any())).thenThrow(NullPointerException.class);
        assertThatThrownBy(() -> {

            this.authService.signup(new UserDTO());
        }).isInstanceOf(EmailNullException.class);
    }

    @Test
    @DisplayName("중복 회원가입")
    public void duplicateEmailSignUpTest(){
        when(this.userDAO.existByEmail(this.userDTO.getEmail())).thenReturn(true);

        assertThatThrownBy(()->{
            this.authService.signup(userDTO);

        }).isInstanceOf(EmailDuplicationException.class);

    }

    @Test
    @DisplayName("로그인")
    public void signInTest(){
        UserEntity userEntity = UserEntity.initUserEntity(
                UserDTO.builder().email(userDTO.getEmail()).password(passwordEncoder.encode(userDTO.getPassword())).build());

        when(this.userDAO.existByEmail(userDTO.getEmail())).thenReturn(true);
        when(this.userDAO.readUser(any())).thenReturn(userEntity);


        TokenDTO after = this.authService.signin(userDTO);


        TokenDTO tokenDTO = TokenDTO.builder().email(userDTO.getEmail()).build();
        this.checkTokenDTO(tokenDTO, after);
    }

    @Test
    @DisplayName("회원가입 이전 로그인")
    public void beforeSignUpSignInTest(){

        when(userDAO.existByEmail(userDTO.getEmail())).thenReturn(false);

        assertThatThrownBy(()->{
            this.authService.signin(userDTO);
        }).isInstanceOf(EmailNotExistException.class);
    }

    @Test
    @DisplayName("잘못된 비밀번호 로그인")
    public void passwordInvalidSignInTest(){
        UserEntity userEntity = UserEntity.initUserEntity(
                UserDTO.builder().email(userDTO.getEmail()).password(passwordEncoder.encode(userDTO.getPassword())).build());

        when(this.userDAO.existByEmail(userDTO.getEmail())).thenReturn(true);
        when(this.userDAO.readUser(any())).thenReturn(userEntity);
        assertThatThrownBy(()->{
            userDTO.setPassword("123");

            this.authService.signin(userDTO);

        }).isInstanceOf(PasswordInvalidException.class);

    }



    private void checkUserDTO(UserDTO before, UserDTO after){
        assertThat(before.getEmail()).isEqualTo(after.getEmail());
    }

    private void checkTokenDTO(TokenDTO before, TokenDTO after){
        assertThat(before.getEmail()).isEqualTo(after.getEmail());
        assertThat(after.getToken()).startsWith("Bearer ");
    }
}
