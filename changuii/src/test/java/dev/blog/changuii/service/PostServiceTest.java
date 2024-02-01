package dev.blog.changuii.service;


import dev.blog.changuii.dao.PostDAO;
import dev.blog.changuii.dto.PostDTO;
import dev.blog.changuii.entity.PostEntity;
import dev.blog.changuii.service.impl.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@Import({PostServiceImpl.class})
public class PostServiceTest {


    @MockBean
    private PostDAO postDAO;

    private final PostService postService;

    private PostDTO post1;
    private PostDTO post2;
    private PostDTO post3;

    public PostServiceTest(
            @Autowired PostService postService
    ){
        this.postService = postService;
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
    @DisplayName("create post test")
    public void createPostTest(){
        PostEntity postEntity = PostEntity.initEntity(post1);
        PostDTO before = PostDTO.entityToDTO(postEntity);

        // given
        when(postDAO.createPost(refEq(postEntity)))
                .thenReturn(postEntity);

        // when
        PostDTO after = this.postService.createPost(post1);


        //then
        assertThat(after).usingRecursiveComparison().isEqualTo(before);

        verify(postDAO).createPost(refEq(postEntity));


    }

    @Test
    @DisplayName("read post test")
    public void readPostTest(){
        long id = 1L;

        PostEntity postEntity = PostEntity.initEntity(post1);
        postEntity.setId(id);

        PostDTO before = PostDTO.entityToDTO(postEntity);

        //given
        when(postDAO.readPost(id))
                .thenReturn(Optional.of(postEntity));

        //when
        PostDTO after = this.postService.readPost(id);

        //then
        assertThat(after).usingRecursiveComparison().isEqualTo(before);

        verify(postDAO).readPost(id);
    }

    @Test
    @DisplayName("readAllPost")
    public void readAllPostTest(){

        PostEntity postEntity1 = PostEntity.initEntity(post1);
        PostEntity postEntity2 = PostEntity.initEntity(post2);
        PostEntity postEntity3 = PostEntity.initEntity(post3);

        List<PostEntity> postEntityList = Arrays
                .asList(postEntity1, postEntity2, postEntity3);
        List<PostDTO> before = PostDTO.entityListToDTOList(postEntityList);


        //given
        when(postDAO.readAllPost())
                .thenReturn(postEntityList);

        //when
        List<PostDTO> after = this.postService.readAllPost();


        //then
        assertThat(after).usingRecursiveComparison().isEqualTo(before);

        verify(postDAO).readAllPost();
    }


    @Test
    @DisplayName("update post test")
    public void updatePostTest(){
        long id = 1L;
        PostEntity postEntity = PostEntity.initEntity(post1);
        postEntity.setId(id);
        PostEntity updateEntity = PostEntity.updateEntity(postEntity, post2);
        PostDTO before = PostDTO.entityToDTO(updateEntity);

        //given
        when(postDAO.readPost(id))
                .thenReturn(Optional.of(postEntity));
        when(postDAO.createPost(refEq(updateEntity)))
                .thenReturn(updateEntity);

        //when
        post2.setId(id);
        PostDTO after = this.postService.updatePost(post2);

        //then
        assertThat(after).usingRecursiveComparison().isEqualTo(before);

        verify(postDAO).readPost(id);
        verify(postDAO).createPost(refEq(updateEntity));
    }


    @Test
    @DisplayName("delete post test")
    public void deletePostTest(){
        long id = 1L;

        //given
        when(postDAO.existsPost(id))
                .thenReturn(true);

        //when
        this.postService.deletePost(id);


        //then
        verify(postDAO).existsPost(id);


    }



}
