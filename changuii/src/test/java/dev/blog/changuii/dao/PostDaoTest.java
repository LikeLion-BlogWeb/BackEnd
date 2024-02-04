//package dev.blog.changuii.dao;
//
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
//
//import static org.assertj.core.api.Assertions.*;
//
//@ActiveProfiles("dev")
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class PostDaoTest {
//
//    private final PostDAO postDAO;
//
//
//    private PostEntity post1;
//    private PostEntity post2;
//
//    public PostDaoTest(
//            @Autowired PostDAO postDAO
//    ){
//        this.postDAO = postDAO;
//    }
//
//    @BeforeEach
//    public void init(){
//        post1 = PostEntity.builder()
//                .title("게시글입니다.").content("테스트용").email("asd123@naver.com")
//                .writeDate(LocalDateTime.parse("2024-02-01T13:43:23")).likes(new ArrayList<>()).views(0L)
//                .comments(new ArrayList<>()).build();
//        post2 = PostEntity.builder()
//                .title("게시글입니다람쥐").content("테스트용용").email("abcdef@naver.com")
//                .writeDate(LocalDateTime.parse("2024-02-03T21:20:23")).likes(new ArrayList<>()).views(0L)
//                .comments(new ArrayList<>()).build();
//    }
//
//    @Test
//    @DisplayName("insert post test")
//    public void createPostTest(){
//
//        //given
//
//
//        //when
//        PostEntity after = postDAO.createPost(post1);
//
//        //then
//        assertThat(after).usingRecursiveComparison().isEqualTo(post1);
//
//
//    }
//
//}
