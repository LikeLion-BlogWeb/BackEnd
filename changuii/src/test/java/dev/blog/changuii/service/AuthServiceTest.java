package dev.blog.changuii.service;

import dev.blog.changuii.config.security.JwtProvider;
import dev.blog.changuii.dao.UserDAO;
import dev.blog.changuii.dto.TokenDTO;
import dev.blog.changuii.dto.UserDTO;
import dev.blog.changuii.entity.UserEntity;
import dev.blog.changuii.exception.*;
import dev.blog.changuii.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@Import({AuthServiceImpl.class})
public class AuthServiceTest {

    private final AuthServiceImpl authService;

    @MockBean
    private UserDAO userDAO;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private JwtProvider jwtProvider;

    private UserDTO user1;
    private UserDTO user2;
    private UserDTO user3;

    public AuthServiceTest(
            @Autowired AuthServiceImpl authService
    ){
        this.authService = authService;
    }

    @BeforeEach
    public void setUp(){
        user1 = new UserDTO("asd123@naver.com", "12345678", "창의");
        user2 = new UserDTO("abcdefg@naver.com", "87654321", "시영");
        user3 = new UserDTO("abc123@daum.net", "24682468", "현민");
    }

    @Test
    @DisplayName("회원가입")
    public void signUpTest(){
        // given
        UserEntity userEntity = UserEntity.initUserEntity(user1);

        when(this.userDAO.createUser(refEq(userEntity))).thenReturn(userEntity);
        when(this.userDAO.existByEmail(userEntity.getEmail())).thenReturn(false);
        when(this.passwordEncoder.encode(userEntity.getPassword())).thenReturn(userEntity.getPassword());

        // when
        UserDTO after = this.authService.signup(user1);

        // then
        assertThat(user1.getEmail()).isEqualTo(after.getEmail());

        verify(userDAO).createUser(refEq(userEntity));
        verify(userDAO).existByEmail(userEntity.getEmail());
        verify(passwordEncoder).encode(userEntity.getPassword());
    }

    @Test
    @DisplayName("Null 회원가입")
    public void nullDataSignUpTest(){
        // given
        when(this.userDAO.existByEmail(null)).thenReturn(false);
        when(this.passwordEncoder.encode(null)).thenThrow(NullPointerException.class);

        //when
        assertThatThrownBy(() -> {

            this.authService.signup(new UserDTO());

            // then
        }).isInstanceOf(EmailNullException.class);

        verify(this.userDAO).existByEmail(null);
        verify(this.passwordEncoder).encode(null);

    }

    @Test
    @DisplayName("중복 회원가입")
    public void duplicateEmailSignUpTest(){
        //given
        when(this.userDAO.existByEmail(this.user1.getEmail())).thenReturn(true);

        //when
        assertThatThrownBy(()->{
            this.authService.signup(user1);

        // then
        }).isInstanceOf(EmailDuplicationException.class);

    }

    @Test
    @DisplayName("로그인")
    public void signInTest(){
        //given
        UserEntity userEntity = UserEntity.initUserEntity(user1);
        String token = "ABCDEFG";

        when(this.userDAO.existByEmail(user1.getEmail())).thenReturn(true);
        when(this.userDAO.readUser(userEntity.getEmail())).thenReturn(Optional.of(userEntity));
        when(this.passwordEncoder.matches(user1.getPassword(), userEntity.getPassword())).thenReturn(true);
        when(this.jwtProvider.createToken(userEntity.getEmail(), userEntity.getRoles())).thenReturn(token);

        //when
        TokenDTO after = this.authService.signin(user1);


        //then
        assertThat(after.getEmail()).isEqualTo(user1.getEmail());
        assertThat(after.getToken()).startsWith("Bearer ").contains(token);

        verify(this.userDAO).existByEmail(user1.getEmail());
        verify(this.userDAO).readUser(userEntity.getEmail());
        verify(this.passwordEncoder).matches(user1.getPassword(), userEntity.getPassword());
        verify(this.jwtProvider).createToken(userEntity.getEmail(), userEntity.getRoles());

    }

    @Test
    @DisplayName("회원가입 이전 로그인")
    public void beforeSignUpSignInTest(){
        //given
        when(userDAO.existByEmail(user1.getEmail())).thenReturn(false);

        //when
        assertThatThrownBy(()->{
            this.authService.signin(user1);
        //then
        }).isInstanceOf(UserNotFoundException.class);
        verify(this.userDAO).existByEmail(user1.getEmail());
    }

    @Test
    @DisplayName("잘못된 비밀번호 로그인")
    public void passwordInvalidSignInTest(){

        //given
        UserEntity userEntity = UserEntity.initUserEntity(user1);
        user1.setPassword("123");

        when(this.userDAO.existByEmail(user1.getEmail())).thenReturn(true);
        when(this.userDAO.readUser(user1.getEmail())).thenReturn(Optional.of(userEntity));
        when(this.passwordEncoder.matches(user1.getPassword(), userEntity.getPassword())).thenReturn(false);

        assertThatThrownBy(()->{
            this.authService.signin(user1);

        // then
        }).isInstanceOf(PasswordInvalidException.class);

        verify(this.userDAO).existByEmail(user1.getEmail());
        verify(this.userDAO).readUser(user1.getEmail());
        verify(this.passwordEncoder).matches(user1.getPassword(), userEntity.getPassword());

    }

}
