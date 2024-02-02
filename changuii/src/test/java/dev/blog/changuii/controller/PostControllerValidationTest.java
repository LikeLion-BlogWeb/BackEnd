package dev.blog.changuii.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import dev.blog.changuii.config.security.JwtProvider;
import dev.blog.changuii.dto.PostDTO;
import dev.blog.changuii.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PostController.class)
public class PostControllerValidationTest {

    private final MockMvc mockMvc;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private PostService postService;

    private PostDTO post1;
    private PostDTO post2;
    private PostDTO post3;

    public PostControllerValidationTest(
            @Autowired MockMvc mockMvc
    ){
        this.mockMvc = mockMvc;
    }

    @BeforeEach
    public void init(){
        post1 = PostDTO.builder()
                .title("게시글입니다.").content("테스트용").email("asd123@naver.com").writeDate("2024-02-01T13:43:23").build();
        post2 = PostDTO.builder()
                .title("게시글입니다람쥐").content("테스트용용").email("abcdefg@naver.com").writeDate("2024-02-02T13:30:23").build();
        post3 = PostDTO.builder()
                .title("게시글입니다람쥐썬더").content("테스트용용용").email("abc123@daum.net").writeDate("2024-02-03T17:20:23").build();
    }


    @Test
    @DisplayName("post title blank create test")
    public void postTitleBlankCreateTest() throws Exception {

        //given
        post1.setTitle("");

        //when
        mockMvc.perform(
                post("/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(post1))
        )
                .andExpect(status().isBadRequest())
                .andDo(print());

        //then
    }

    @Test
    @DisplayName("post content blank create test")
    public void postContentBlankCreateTest() throws Exception {

        //given
        post1.setContent("");

        //when
        mockMvc.perform(
                post("/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(post1))
        )
                .andExpect(status().isBadRequest())
                .andDo(print());

        //then
    }


    @Test
    @DisplayName("post email blank create test")
    public void postEmailBlankCreateTest() throws Exception {

        //given
        post1.setEmail("");


        //when
        mockMvc.perform(
                post("/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(post1))
        )
                .andExpect(status().isBadRequest())
                .andDo(print());

        //then

    }

    @Test
    @DisplayName("email invalid create test")
    public void postEmailInvalidCreateTest() throws Exception {


        //givne
        post1.setEmail("asd");

        //when
        mockMvc.perform(
                post("/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(post1))
        )
                .andExpect(status().isBadRequest())
                .andDo(print());

        //then

    }




}
