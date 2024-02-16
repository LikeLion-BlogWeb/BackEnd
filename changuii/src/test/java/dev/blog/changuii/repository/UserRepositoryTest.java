//package dev.blog.changuii.repository;
//
//
//import dev.blog.changuii.dto.UserDTO;
//import dev.blog.changuii.entity.UserEntity;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.*;
//
//// 사용할 프로파일 설정
//@ActiveProfiles("dev")
//@DataJpaTest
//// 내장 데이터베이스가 아닌 프로파일에 설정된 데이터베이스 사용
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class UserRepositoryTest {
//
//    private final UserRepository userRepository;
//    private UserEntity user1;
//    private UserEntity user2;
//
//
//    public UserRepositoryTest(
//            @Autowired UserRepository userRepository
//    ){
//        this.userRepository = userRepository;
//    }
//
//    @BeforeEach
//    public void init(){
//        user1 = UserEntity.initUserEntity(
//               UserDTO.builder().email("asd123@naver.com").password("12345678").build()
//        );
//        user2 = UserEntity.initUserEntity(
//                UserDTO.builder().email("abcdefg@daum.net").password("24682468").build()
//        );
//    }
//
//
//    @Test
//    @DisplayName("find By Email test")
//    public void findByEmailTest(){
//
//        //given
//        this.userRepository.save(user1);
//
//        //when
//        Optional<UserEntity> after = this.userRepository.findByEmail(user1.getEmail());
//
//        //then
//        assertThat(after.get()).isEqualTo(user1);
//    }
//
//
//    @Test
//    @DisplayName("exists by email test")
//    public void existsByEmailTest(){
//
//        //given
//        this.userRepository.save(user1);
//
//        //when
//        boolean after1 = this.userRepository.existsByEmail(user1.getEmail());
//        boolean after2 = this.userRepository.existsByEmail(user2.getEmail());
//
//        //then
//        assertThat(after1).isTrue();
//        assertThat(after2).isFalse();
//    }
//
//    @Test
//    @DisplayName("delete by email test")
//    public void deleteByEmailTest(){
//
//        //given
//        this.userRepository.save(user1);
//
//        //when
//        int after1 = this.userRepository.deleteByEmail(user1.getEmail());
//        int after2 = this.userRepository.deleteByEmail(user2.getEmail());
//
//        //then
//        assertThat(after1).isEqualTo(1);
//        assertThat(after2).isEqualTo(0);
//
//    }
//
//
//}
