package dev.blog.changuii.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.blog.changuii.config.security.JwtProvider;
import dev.blog.changuii.dto.PostDTO;
import dev.blog.changuii.dto.ResponsePostDTO;
import dev.blog.changuii.entity.PostEntity;
import dev.blog.changuii.entity.UserEntity;
import dev.blog.changuii.exception.PostNotFoundException;
import dev.blog.changuii.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
public class PostControllerTest {


    private final MockMvc mockMvc;
    @MockBean
    private PostService postService;
    // spring security filter 의존성
    @MockBean
    private JwtProvider jwtProvider;
    //

    private PostDTO post1;
    private PostDTO post2;
    private PostDTO post3;

    public PostControllerTest(
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
    @DisplayName("post create test")
    public void createPostTest() throws Exception {
        long id = 1L;
        PostEntity postEntity = PostEntity.initEntity(post1, UserEntity.builder().email(post1.getEmail()).build());
        postEntity.setId(id);
        ResponsePostDTO after = PostEntity.toResponseDTO(postEntity);
        //given
        given(postService.createPost(refEq(post1)))
                .willReturn(after);

        //when
        mockMvc.perform(
                post("/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(post1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value(post1.getTitle()))
                .andExpect(jsonPath("$.content").value(post1.getContent()))
                //
                .andExpect(jsonPath("$.user.email").value(post1.getEmail()))
                .andExpect(jsonPath("$.like").value(new ArrayList<>()))
                .andExpect(jsonPath("$.views").value(0))
                .andDo(print());
        //then
        verify(postService).createPost(refEq(post1));
    }

    @Test
    @DisplayName("read post test")
    public void readPostTest() throws Exception {
        long id = 1L;
        PostEntity postEntity = PostEntity.initEntity(post1, UserEntity.builder().email(post1.getEmail()).name("창의").build());
        postEntity.setId(id);
        ResponsePostDTO after = PostEntity.toResponseDTO(postEntity);
        //given
        given(postService.readPost(id))
                .willReturn(after);

        //when
        mockMvc.perform(
                get("/post/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value(post1.getTitle()))
                .andExpect(jsonPath("$.content").value(post1.getContent()))
                // 변경 ResponsePostDTO는 이메일이 아닌 userDTO 반환
                .andExpect(jsonPath("$.user.email").value(post1.getEmail()))
                .andExpect(jsonPath("$.user.name").value("창의"))
                .andExpect(jsonPath("$.like").value(new ArrayList<>()))
                .andExpect(jsonPath("$.views").value(0))
                .andDo(print());

        //then
        verify(postService).readPost(id);
    }

    @Test
    @DisplayName("post not found read test")
    public void postNotFoundReadTest() throws Exception {
        long id = 1L;
        Throwable e = new PostNotFoundException();

        //given
        given(postService.readPost(id))
                .willThrow(e);

        //when
        mockMvc.perform(
                        get("/post/"+id)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(e.getMessage()))
                .andDo(print());

        //then
        verify(postService).readPost(id);

    }

    @Test
    @DisplayName("read all post test")
    public void readAllPostTest() throws Exception {
        PostEntity postEntity1 = PostEntity.initEntity(post1, UserEntity.builder().email(post1.getEmail()).name("창의").build());
        PostEntity postEntity2 = PostEntity.initEntity(post2, UserEntity.builder().email(post2.getEmail()).name("시영").build());
        PostEntity postEntity3 = PostEntity.initEntity(post3, UserEntity.builder().email(post3.getEmail()).name("현민").build());
        postEntity1.setId(1L);
        postEntity2.setId(2L);
        postEntity3.setId(3L);

        ResponsePostDTO after1 = PostEntity.toResponseDTO(postEntity1);
        ResponsePostDTO after2 = PostEntity.toResponseDTO(postEntity2);
        ResponsePostDTO after3 = PostEntity.toResponseDTO(postEntity3);
        List<ResponsePostDTO> postDTOList = Arrays.asList(after1, after2, after3);

        //given
        given(postService.readAllPost())
                .willReturn(postDTOList);

        //when
        MvcResult result = mockMvc.perform(
                get("/post")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                // 직접 비교와 Response를 List로 변환 후 비교하는 방법이 있음
//                // after1
//                .andExpect(jsonPath("$[0].id").value(after1.getId()))
//                .andExpect(jsonPath("$[0].title").value(after1.getTitle()))
//                .andExpect(jsonPath("$[0].content").value(after1.getContent()))
//                .andExpect(jsonPath("$[0].email").value(after1.getEmail()))
//                .andExpect(jsonPath("$[0].like").value(after1.getLike()))
//                .andExpect(jsonPath("$[0].views").value(after1.getViews()))
//                // after2
//                .andExpect(jsonPath("$[1].id").value(after2.getId()))
//                .andExpect(jsonPath("$[1].title").value(after2.getTitle()))
//                .andExpect(jsonPath("$[1].content").value(after2.getContent()))
//                .andExpect(jsonPath("$[1].email").value(after2.getEmail()))
//                .andExpect(jsonPath("$[1].like").value(after2.getLike()))
//                .andExpect(jsonPath("$[1].views").value(after2.getViews()))
//                // after3
//                .andExpect(jsonPath("$[2].id").value(after3.getId()))
//                .andExpect(jsonPath("$[2].title").value(after3.getTitle()))
//                .andExpect(jsonPath("$[2].content").value(after3.getContent()))
//                .andExpect(jsonPath("$[2].email").value(after3.getEmail()))
//                .andExpect(jsonPath("$[2].like").value(after3.getLike()))
//                .andExpect(jsonPath("$[2].views").value(after3.getViews()))
                        .andDo(print()).andReturn();

        // 이 방식을 사용할때 서블릿 설정으로 utf-8을 설정해야 응답의 한글값이 깨지지 않는다.
        ObjectMapper mapper = new ObjectMapper();
        List<ResponsePostDTO> after = mapper.readValue(
                result.getResponse().getContentAsString()
                , new TypeReference<List<ResponsePostDTO>>() {});

        //then
        verify(postService).readAllPost();

        assertThat(after).usingRecursiveComparison().isEqualTo(postDTOList);

    }

    @Test
    @DisplayName("update post test")
    public void updatePostTest() throws Exception {
        PostEntity postEntity = PostEntity.initEntity(post1, UserEntity.builder().email(post1.getEmail()).build());
        postEntity.setId(1L);
        PostDTO after1 = PostEntity.toDTO(postEntity);

        //given
        given(postService.updatePost(refEq(after1)))
                .willReturn(PostEntity.toResponseDTO(postEntity));
        //when
        mockMvc.perform(
                put("/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(after1))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(after1.getId()))
                .andExpect(jsonPath("$.title").value(after1.getTitle()))
                .andExpect(jsonPath("$.content").value(after1.getContent()))
                // ReponsePostDTO로 변경 email -> userDTO
                .andExpect(jsonPath("$.user.email").value(after1.getEmail()))

                .andExpect(jsonPath("$.like").value(after1.getLike()))
                .andExpect(jsonPath("$.views").value(after1.getViews()))
                .andDo(print());

        //then
        verify(postService).updatePost(refEq(after1));
    }

    @Test
    @DisplayName("not found post update test")
    public void notFoundPostUpdateTest() throws Exception {
        PostDTO after1 = PostEntity.toDTO(PostEntity.initEntity(post1, UserEntity.builder().email(post1.getEmail()).build()));
        after1.setId(1L);

        Throwable e = new PostNotFoundException();

        //given
        given(postService.updatePost(refEq(after1)))
                .willThrow(e);
        //when
        mockMvc.perform(
                        put("/post")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(after1))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(e.getMessage()))
                .andDo(print());

        //then
        verify(postService).updatePost(refEq(after1));
    }

    @Test
    @DisplayName("delete post test")
    public void deletePostTest() throws Exception {


        //given


        //when
        mockMvc.perform(
                delete("/post/"+1L)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andDo(print());

        //then
        verify(postService).deletePost(1L);

    }

    @Test
    @DisplayName("not found post delete test")
    public void notFoundPostDeleteTest() throws Exception {
        Throwable e = new PostNotFoundException();

        //given
        // void 메소드 예외 던지기
        doThrow(e).when(postService).deletePost(1L);

        //when
        mockMvc.perform(
                        delete("/post/"+1L)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(e.getMessage()))
                .andDo(print());

        //then
        verify(postService).deletePost(1L);

    }

}
