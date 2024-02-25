package dev.blog.changuii.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import dev.blog.changuii.config.security.JwtProvider;
import dev.blog.changuii.dto.UserDTO;
import dev.blog.changuii.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerValidationTest {

    private final static Logger logger = LoggerFactory.getLogger(AuthControllerValidationTest.class);
    private final MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtProvider jwtProvider;


    private UserDTO user1;
    private UserDTO user2;
    private UserDTO user3;

    public AuthControllerValidationTest(
            @Autowired MockMvc mockMvc
    ){
        this.mockMvc = mockMvc;
    }



    @BeforeEach
    public void init(){
        user1 = new UserDTO("asd123@naver.com", "12345678", "창의");
        user2 = new UserDTO("abcdefg@naver.com", "87654321", "시영");
        user3 = new UserDTO("abc123@daum.net", "24682468", "현민");

    }


    @Test
    @DisplayName("email validation test")
    public void emailValidationTest() throws Exception {

        //given
        List<String> testcase = Arrays.asList("", " ", null, "asd");

        //when

        for(String test : testcase) {
            user1.setEmail(test);
            logger.info("===============================");
            logger.info("test : "+test);
            mockMvc.perform(
                            post("/auth/signup")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(new ObjectMapper().writeValueAsString(user1))
                    )
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }
        //then

    }

    @Test
    @DisplayName("password validation test")
    public void passwordValidationTest() throws Exception {

        //given
        List<String> testcase = Arrays.asList("", " ", null, "1234567");

        //when

        for(String test : testcase){
            user1.setPassword(test);
            logger.info("===============================");
            logger.info("test : "+test);
            mockMvc.perform(
                            post("/auth/signup")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(new ObjectMapper().writeValueAsString(user1))
                    )
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }

        //then


    }




}
