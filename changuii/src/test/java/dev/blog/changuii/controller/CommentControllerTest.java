package dev.blog.changuii.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.blog.changuii.config.security.JwtProvider;
import dev.blog.changuii.dto.CommentDTO;
import dev.blog.changuii.dto.UserDTO;
import dev.blog.changuii.dto.response.ResponseCommentDTO;
import dev.blog.changuii.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.*;


@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @MockBean
    private CommentService commentService;

    @MockBean
    private JwtProvider jwtProvider;

    private final MockMvc mockMvc;

    private CommentDTO comment1;
    private CommentDTO comment2;
    private Long id = 0L;

    public CommentControllerTest(
            @Autowired MockMvc mockMvc
    ){
        this.mockMvc = mockMvc;
    }

    @BeforeEach
    public void init(){
        comment1 = CommentDTO.builder()
                .email("asd123@naver.com").content("글이 정말 좋아요")
                .postId(1L).writeDate("2024-02-05T16:02:32")
                .build();
        comment2 = CommentDTO.builder()
                .email("abcdefg@naver.com").content("잘 보고 갑니다.")
                .postId(1L).writeDate("2024-02-28T22:02:32")
                .build();
    }

    private ResponseCommentDTO setId(CommentDTO comment){
        return ResponseCommentDTO.builder()
                .user(UserDTO.builder().email(comment.getEmail()).build())
                .content(comment.getContent())
                .postId(comment.getPostId())
                .writeDate(comment.getWriteDate())
                .id(this.id++).build();
    }

    @Test
    @DisplayName("댓글 작성")
    public void createComment() throws Exception {
        ResponseCommentDTO returnValue = setId(comment1);

        //given
        given(commentService.createComment(refEq(comment1)))
                .willReturn(returnValue);

        //when
        mockMvc.perform(
                post("/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(comment1))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(returnValue.getId()))
                .andExpect(jsonPath("$.content").value(returnValue.getContent()))
                .andExpect(jsonPath("$.writeDate").value(returnValue.getWriteDate()))
                .andExpect(jsonPath("$.user.email").value(returnValue.getUser().getEmail()))
                .andExpect(jsonPath("$.postId").value(returnValue.getPostId()))
                .andDo(print());

        //then
        verify(commentService).createComment(refEq(comment1));
    }

    @Test
    @DisplayName("댓글 id 기반 조회")
    public void readComment() throws Exception{
        ResponseCommentDTO returnValue = setId(comment1);
        long id = returnValue.getId();

        //given
        given(commentService.readComment(id))
                .willReturn(returnValue);

        //when
        mockMvc.perform(
                        get("/comment/"+id)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(returnValue.getId()))
                .andExpect(jsonPath("$.content").value(returnValue.getContent()))
                .andExpect(jsonPath("$.writeDate").value(returnValue.getWriteDate()))
                .andExpect(jsonPath("$.user.email").value(returnValue.getUser().getEmail()))
                .andExpect(jsonPath("$.postId").value(returnValue.getPostId()))
                .andDo(print());

        //then
        verify(commentService).readComment(id);
    }

    @Test
    @DisplayName("댓글 전체 조회")
    public void readAllComment() throws Exception{
        List<ResponseCommentDTO> comments =
                Arrays.asList(setId(comment1), setId(comment2));

        //given
        given(this.commentService.readAllComment())
                .willReturn(comments);

        //when
        MvcResult mvcResult = mockMvc.perform(
                get("/comment")
        )
                .andExpect(status().isOk())
                .andDo(print()).andReturn();

        //then
        List<ResponseCommentDTO> after = new ObjectMapper()
                .readValue(
                        mvcResult.getResponse().getContentAsString(),
                        new TypeReference<List<ResponseCommentDTO>>() {}
                );

        verify(commentService).readAllComment();
        assertThat(after).usingRecursiveComparison().isEqualTo(comments);


    }

    @Test
    @DisplayName("게시글 기반 댓글 전체 조회")
    public void readAllByPostComment() throws Exception {
        long postId = comment1.getPostId();
        List<ResponseCommentDTO> comments =
                Arrays.asList(setId(comment1), setId(comment2));

        //given
        given(this.commentService.readAllByPostComment(postId))
                .willReturn(comments);

        //when
        MvcResult mvcResult = mockMvc.perform(
                        get("/comment/post/"+postId)
                )
                .andExpect(status().isOk())
                .andDo(print()).andReturn();

        //then
        List<ResponseCommentDTO> after = new ObjectMapper().readValue(
                        mvcResult.getResponse().getContentAsString(),
                        new TypeReference<List<ResponseCommentDTO>>() {}
                );

        verify(commentService).readAllByPostComment(postId);
        assertThat(after).usingRecursiveComparison().isEqualTo(comments);

    }

    @Test
    @DisplayName("댓글 수정")
    public void updateComment() throws Exception {
        ResponseCommentDTO before = setId(comment1);
        long id = before.getId();
        comment1.setContent("댓글을 수정했어 이건 이렇고 저건 저렇고");
        comment1.setId(id);


        //given
        before.setContent(comment1.getContent());
        given(this.commentService.updateComment(refEq(comment1)))
                .willReturn(before);
        //when

        MvcResult mvcResult = mockMvc.perform(
                put("/comment/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(comment1))
        )
                .andExpect(status().isOk())
                .andDo(print()).andReturn();

        //then
        ResponseCommentDTO after = new ObjectMapper()
                .readValue(
                        mvcResult.getResponse().getContentAsString(),
                        new TypeReference<ResponseCommentDTO>() {}
                );

        verify(this.commentService).updateComment(refEq(comment1));
        assertThat(before).usingRecursiveComparison().isEqualTo(after);

    }

    @Test
    @DisplayName("댓글 삭제")
    public void deleteComment() throws Exception {
        long id = 1;

        //given


        //when
        mockMvc.perform(
                delete("/comment/"+id)
        )
                .andExpect(status().isOk())
                .andDo(print());


        //then
        verify(commentService).deleteComment(id);

    }


}
