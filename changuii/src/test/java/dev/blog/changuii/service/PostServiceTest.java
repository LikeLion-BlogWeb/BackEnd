package dev.blog.changuii.service;


import dev.blog.changuii.dao.PostDAO;
import dev.blog.changuii.dao.UserDAO;
import dev.blog.changuii.dto.PostDTO;
import dev.blog.changuii.dto.response.ResponsePostDTO;
import dev.blog.changuii.dto.UserDTO;
import dev.blog.changuii.entity.PostEntity;
import dev.blog.changuii.entity.UserEntity;
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

    @MockBean
    private UserDAO userDAO;

    private final PostService postService;

    private PostDTO post1;
    private PostDTO post2;
    private PostDTO post3;
    private UserDTO user1;
    private UserDTO user2;
    private UserDTO user3;

    private UserEntity userEntity1;

    private PostEntity postEntity1;
    private PostEntity postEntity2;
    private PostEntity postEntity3;

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

        user1 = UserDTO.builder()
                .email("asd123@naver.com").password("1234").name("창의").build();
        user2 = UserDTO.builder()
                .email("abcdefg@naver.com").password("1234").name("시영").build();
        user3 = UserDTO.builder()
                .email("abc123@daum.net").password("1234").name("현민").build();

        userEntity1 = UserDTO.toEntity(user1);


        postEntity1 = PostDTO.toEntity(post1, userEntity1);
        postEntity2 = PostDTO.toEntity(post2, UserDTO.toEntity(user2));
        postEntity3 = PostDTO.toEntity(post3, UserDTO.toEntity(user3));

    }


    @Test
    @DisplayName("create post test")
    public void createPostTest(){
        PostEntity afterPostEntity = PostDTO.toEntity(post1, userEntity1);

        afterPostEntity.setId(1L);
        ResponsePostDTO before = PostEntity.toResponseDTO(afterPostEntity, entity->false);



        // given
        when(postDAO.createPost(refEq(postEntity1)))
                .thenReturn(afterPostEntity);
        when(userDAO.readUser(post1.getEmail()))
                .thenReturn(Optional.of(userEntity1));


        // when
        ResponsePostDTO after = this.postService.createPost(post1);


        //then
        assertThat(after).usingRecursiveComparison().isEqualTo(before);

        verify(postDAO).createPost(refEq(postEntity1));
        verify(userDAO).readUser(post1.getEmail());

    }

    @Test
    @DisplayName("read post test")
    public void readPostTest(){
        long id = 1L;
        postEntity1.setId(id);

        ResponsePostDTO before = PostEntity.toResponseDTO(postEntity1, entity->true);

        //given
        when(postDAO.readPost(id))
                .thenReturn(Optional.of(postEntity1));

        //when
        ResponsePostDTO after = this.postService.readPost(id);

        //then
        assertThat(after).usingRecursiveComparison().isEqualTo(before);

        verify(postDAO).readPost(id);
    }

    @Test
    @DisplayName("readAllPost")
    public void readAllPostTest(){
        postEntity1.setId(1L);
        postEntity2.setId(2L);
        postEntity3.setId(3L);

        List<PostEntity> postEntityList = Arrays
                .asList(postEntity1, postEntity2, postEntity3);
        List<ResponsePostDTO> before = PostEntity.toResponseDTOs(postEntityList, entity->false);


        //given
        when(postDAO.readAllOrderByWriteDatePost())
                .thenReturn(postEntityList);

        //when
        List<ResponsePostDTO> after = this.postService.readAllPost();

        //then
        assertThat(after).usingRecursiveComparison().isEqualTo(before);

        verify(postDAO).readAllOrderByWriteDatePost();
    }


    @Test
    @DisplayName("update post test")
    public void updatePostTest(){
        long id = 1L;
        postEntity1.setId(id);

        PostEntity updateEntity = PostEntity.updateEntity(postEntity1, post2);
        ResponsePostDTO before = PostEntity.toResponseDTO(updateEntity, entity->false);

        //given
        when(postDAO.readPost(id))
                .thenReturn(Optional.of(postEntity1));
        when(postDAO.createPost(refEq(updateEntity)))
                .thenReturn(updateEntity);

        //when
        post2.setId(id);
        ResponsePostDTO after = this.postService.updatePost(post2);

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
