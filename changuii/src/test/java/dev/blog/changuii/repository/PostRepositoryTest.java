//package dev.blog.changuii.repository;
//
//import dev.blog.changuii.dao.PostDAO;
//import dev.blog.changuii.entity.CommentEntity;
//import dev.blog.changuii.entity.PostEntity;
//import dev.blog.changuii.entity.UserEntity;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.*;
//
//@ActiveProfiles("dev")
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class PostRepositoryTest {
//
//    private final PostRepository postRepository;
//
//    private final CommentRepository commentRepository;
//
//    private PostEntity post1;
//    private PostEntity post2;
//
//    public PostRepositoryTest(
//            @Autowired PostRepository postRepository,
//            @Autowired CommentRepository commentRepository
//    ){
//        this.postRepository = postRepository;
//        this.commentRepository = commentRepository;
//    }
//
//    @BeforeEach
//    public void init(){
//        post1 = PostEntity.builder()
//                .title("게시글입니다.").content("테스트용")
//                .userEntity(UserEntity.builder().email("asd123@naver.com").build())
//                .writeDate(LocalDateTime.parse("2024-02-01T13:43:23")).likes(new ArrayList<>()).views(0L)
//                .comments(new ArrayList<>()).build();
//        post2 = PostEntity.builder()
//                .title("게시글입니다람쥐").content("테스트용용")
//                .userEntity(UserEntity.builder().email("abcdef@naver.com").build())
//                .writeDate(LocalDateTime.parse("2024-02-03T21:20:23")).likes(new ArrayList<>()).views(0L)
//                .comments(new ArrayList<>()).build();
//    }
//
//
//    @Test
//    @DisplayName("insert test")
//    public void insertTest(){
//        //given
//
//        //when
//        PostEntity after = postRepository.save(post1);
//
//
//        //then
//        assertThat(after).usingRecursiveComparison().isEqualTo(post1);
//
//    }
//    @Test
//    @DisplayName("insert comments test")
//    public void insertCommentsTest(){
//        //given
//        List<CommentEntity> comments = post1.getComments();
//        CommentEntity comment = CommentEntity.builder()
//                        .post(post1).writeDate(post1.getWriteDate())
//                        .user(post1.getUserEntity()).content("asdasd").build();
//        comments.add(comment);
//        post1.setComments(comments);
//
//        //when
//        PostEntity after = postRepository.save(post1);
//
//
//        //then
//        assertThat(after).usingRecursiveComparison().isEqualTo(post1);
//
//    }
//
//    @Test
//    @DisplayName("insert comments and select")
//    public void readCommentsTest(){
//        //given
//        List<CommentEntity> comments = post1.getComments();
//        CommentEntity comment = CommentEntity.builder()
//                .post(post1).writeDate(post1.getWriteDate())
//                .user(post1.getUserEntity()).content("asdasd").build();
//        comments.add(comment);
//        post1.setComments(comments);
//
//        //when
//        PostEntity after = postRepository.save(post1);
//        comments = commentRepository.findAllByPost(post1);
//
//
//        //then
//        assertThat(after).usingRecursiveComparison().isEqualTo(post1);
//        assertThat(comments.get(0)).usingRecursiveComparison().isEqualTo(comment);
//    }
//
//}
