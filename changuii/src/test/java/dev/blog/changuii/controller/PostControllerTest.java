package dev.blog.changuii.controller;


import dev.blog.changuii.service.PostService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PostController.class)
public class PostControllerTest {


    private final MockMvc mockMvc;
    @MockBean
    private PostService postService;






}
