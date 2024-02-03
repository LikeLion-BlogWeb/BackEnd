package dev.blog.changuii.repository;


import dev.blog.changuii.entity.CommentEntity;
import dev.blog.changuii.entity.PostEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;


//@ActiveProfiles("dev")
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class CommentRepositoryTest {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    private CommentEntity comment1;
    private CommentEntity comment2;
    private PostEntity post1;

    public CommentRepositoryTest(
            @Autowired CommentRepository commentRepository,
            @Autowired PostRepository postRepository
    ){
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @BeforeEach
    public void init(){
        post1 = PostEntity.builder()
                .title("게시글입니다람쥐").content("테스트용용").email("abcdef@naver.com")
                .writeDate(LocalDateTime.parse("2024-02-03T21:20:23")).likes(new ArrayList<>()).views(0L)
                .comments(new ArrayList<>()).build();
        comment1 = CommentEntity.builder()
                .email("asd@naver.com").content("테스트")
                .writeDate(LocalDateTime.now()).build();
        comment2 = CommentEntity.builder()
                .email("asdfeqwer@naver.com").content("테스트용용")
                .writeDate(LocalDateTime.now()).build();
    }


    @Test
    @DisplayName("post 없이 댓글 insert")
    public void notPostCreateComment(){

        //given
        comment1.setPost(post1);
        comment2.setPost(post1);


        //when
        PostEntity p = postRepository.save(post1);
        commentRepository.save(comment1);
        commentRepository.save(comment2);

        postRepository.flush();


        postRepository.deleteById(post1.getId());

        postRepository.flush();

    }
}
