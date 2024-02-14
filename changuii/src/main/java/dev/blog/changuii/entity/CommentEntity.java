package dev.blog.changuii.entity;

import dev.blog.changuii.dto.CommentDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comment")
@ToString
public class CommentEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column
    private LocalDateTime writeDate;

    // Lazy가 권장된다.
    // Post의 정보를 읽어올 때(Post객체 내부 정보에 접근할 때)만 Post값을 가져오기위해 쿼리문이 실행된다.
    @ManyToOne(fetch = FetchType.LAZY)
    // 연관관계에서 사용할 외래키 지정 (테이블 이름, 클래스 필드 이름 x)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    private UserEntity user;


    public static CommentEntity DtoToEntity(CommentDTO commentDTO, UserEntity user, PostEntity post){
        return CommentEntity.builder()
                .content(commentDTO.getContent())
                .writeDate(LocalDateTime.parse(commentDTO.getWriteDate()))
                .post(post)
                .user(user)
                .build();
    }


    public static CommentEntity updateComment(CommentEntity origin, CommentDTO updateValue){
        return CommentEntity.builder()
                // 수정 불가능
                .id(origin.getId())
                .post(origin.getPost())
                .user(origin.getUser())
                .writeDate(origin.getWriteDate())

                // 수정 가능
                .content(updateValue.getContent())
                .build();
    }

    public static List<CommentEntity> descByWriteDateComment(List<CommentEntity> comments){
        Collections.sort(comments, new Comparator<CommentEntity>() {
            @Override
            public int compare(CommentEntity o1, CommentEntity o2) {
                if(o1.writeDate.isBefore(o2.writeDate)){
                    return -1;
                }else{
                    return 1;
                }
            }
        });
        return comments;


    }


}
