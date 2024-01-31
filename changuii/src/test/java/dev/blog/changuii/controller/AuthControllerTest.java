package dev.blog.changuii.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.blog.changuii.config.security.JwtProvider;
import dev.blog.changuii.dto.TokenDTO;
import dev.blog.changuii.dto.UserDTO;
import dev.blog.changuii.exception.EmailDuplicationException;
import dev.blog.changuii.exception.EmailNullException;
import dev.blog.changuii.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    // http를 테스트하기 위한 객체
    private final MockMvc mockMvc;

    // AuthController가 의존하고 있는 authService의 역할을 수행한다.
    @MockBean
    private AuthService authService;


    // filter 의존성 주입
    @MockBean
    private JwtProvider jwtProvider;

    private UserDTO user1;
    private UserDTO user2;
    private UserDTO user3;


    public AuthControllerTest(
            @Autowired MockMvc mockMvc
    ){
        this.mockMvc = mockMvc;
    }


    @Test
    @DisplayName("회원가입 테스트")
    public void signUpTest() throws Exception {

        //given
        // refEq를 통해 동등성 비교
        given(authService.signup(refEq(user1)))
                .willReturn(UserDTO.builder().email(user1.getEmail()).build());

        // when
        // restAPI 를 테스트하기 위한 객체
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        // post body값 설정 (UserDTO를 json string으로 변환)
                        .content(new ObjectMapper().writeValueAsString(user1)))
                .andExpect(status().isCreated())
                // json의 depth가 깊어지면, .을 추가하여 탐색할 수 있음 (ex: $.productId.productIdName)
                .andExpect(jsonPath("$.email").value(user1.getEmail()))
                .andExpect(jsonPath("$.password").isEmpty())
                .andDo(print());

        // then
        // 해당 메소드가 실행되었는지를 검증할 수 있다. (eq 메소드로 동등성 비교)
        verify(authService).signup(refEq(user1));


    }

    @Test
    @DisplayName("중복 이메일 가입")
    public void duplicationSignUp() throws Exception {

        //given
        given(authService.signup(refEq(user1)))
                .willThrow(new EmailDuplicationException("잘못된 요청입니다."));

        //when
        mockMvc.perform(
                post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user1))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.password").isEmpty())
                .andDo(print());

        //then
        verify(authService).signup(refEq(user1));
    }

    @Test
    @DisplayName("null 회원가입")
    public void nullEmailSignUpTest() throws Exception {

        user1.setEmail(null);

        //given
        given(authService.signup(refEq(user1)))
                .willThrow(new EmailNullException("잘못된 요청입니다."));

        //when
        mockMvc.perform(
                post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user1))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.password").isEmpty())
                .andDo(print());

        // then
        verify(authService).signup(refEq(user1));


    }


    @Test
    @DisplayName("로그인")
    public void signInTest() throws Exception {

        String token = "Bearer ";
        // given
        given(authService.signin(refEq(user1)))
                .willReturn(TokenDTO.makeTokenDTO(user1.getEmail(), token));

        //when
        mockMvc.perform(
                post("/auth/signin")
                        .content(new ObjectMapper().writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(user1.getEmail()))
                .andExpect(jsonPath("$.token").value(token))
                .andDo(print());

        // then
        verify(authService).signin(refEq(user1));
    }




    @BeforeEach
    public void init(){
        user1 = new UserDTO("asd123@naver.com", "12345678");
        user2 = new UserDTO("abcdefg@naver.com", "87654321");
        user3 = new UserDTO("abc123@daum.net", "24682468");

    }


}
