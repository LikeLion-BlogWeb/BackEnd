package dev.blog.changuii.service;


import dev.blog.changuii.dao.CommentDAO;
import dev.blog.changuii.dao.PostDAO;
import dev.blog.changuii.dao.UserDAO;
import dev.blog.changuii.dto.CommentDTO;
import dev.blog.changuii.entity.CommentEntity;
import dev.blog.changuii.entity.PostEntity;
import dev.blog.changuii.entity.UserEntity;
import dev.blog.changuii.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@Import({CommentServiceImpl.class})
public class CommentServiceTest {

    private final CommentService commentService;

    @MockBean
    private CommentDAO commentDAO;

    @MockBean
    private PostDAO postDAO;

    @MockBean
    private UserDAO userDAO;

    private CommentDTO comment1;
    private CommentDTO comment2;
    private UserEntity user1;
    private UserEntity user2;
    private PostEntity post1;

    public CommentServiceTest(
            @Autowired CommentService commentService
    ){
        this.commentService = commentService;
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
        user1 = UserEntity.builder()
                .email("asd123@naver.com").build();
        user2 = UserEntity.builder()
                .email("abcdefg@naver.com").build();
        post1 = PostEntity.builder()
                .id(1L).build();
    }

    @Test
    @DisplayName("댓글 생성")
    public void createCommentTest(){
        long id = 1L;
        comment1.setId(id);
        CommentEntity comment = CommentEntity
                .DtoToEntity(comment1, user1, post1);
        CommentDTO before = CommentDTO.EntityToDTO(comment);

        //given
        when(this.commentDAO.createComment(refEq(comment)))
                .thenReturn(comment);
        when(this.userDAO.readUser(user1.getEmail()))
                .thenReturn(Optional.of(user1));
        when(this.postDAO.readPost(post1.getId()))
                .thenReturn(Optional.of(post1));

        //when
        CommentDTO after =
                this.commentService.createComment(comment1);


        //then
        verify(this.commentDAO).createComment(refEq(comment));
        verify(this.userDAO).readUser(user1.getEmail());
        verify(this.postDAO).readPost(post1.getId());

        assertThat(after).usingRecursiveComparison().isEqualTo(before);
    }

    @Test
    @DisplayName("댓글 조회")
    public void readCommentTest(){
        long id = 1L;
        comment1.setId(id);
        CommentEntity comment = CommentEntity
                .DtoToEntity(comment1, user1, post1);
        CommentDTO before = CommentDTO.EntityToDTO(comment);

        //given
        when(this.commentDAO.readComment(id))
                .thenReturn(Optional.of(comment));

        //when
        CommentDTO after = this.commentService.readComment(id);


        //then
        verify(this.commentDAO).readComment(id);

        assertThat(after).usingRecursiveComparison().isEqualTo(before);
    }



}
