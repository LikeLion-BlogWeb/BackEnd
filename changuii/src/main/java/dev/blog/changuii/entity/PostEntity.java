package dev.blog.changuii.entity;

import dev.blog.changuii.dto.PostDTO;
import dev.blog.changuii.dto.response.ResponsePostDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "post")
@ToString
public class PostEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private long id;

    @Column
    private String title;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    private UserEntity user;

    @Column
    private LocalDateTime writeDate;

    // SQL 예약어와 동일한 컬럼 사용불가 !!
    @Column
    @Builder.Default
    @ElementCollection
    private List<String> likes = new ArrayList<>();

    @Column
    private Long views;

    @Column
    private String category;

//    김영한
//    안녕하세요. donald님
//    이 경우 OnDelete, OnUpdate같은 방법을 사용하는 것은 오히려 복잡해지고, 좋지 않은 방법입니다.
//    JPA는 객제지향 스타일로 개발 하는 것이 원칙입니다.
//    따라서 부모 엔티티를 삭제하기 전에 자식 엔티티를 찾고, 부모 엔티티와 연관관계를 null 처리 하는 것이 맞습니다.

    // mappedBy가 없는 쪽이 연관관계의 주인이 된다. (+ JoinColumn)
    // 오직 연관관계의 주인만 연관관계를 수정할 수 있다. (오직 조회만 가능)
    // 객체 사이의 연관관계는 참조를 통해 찾아낸다.
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    // toString 무한 반복 방지(stack overflow)
    @ToString.Exclude
    // Builder 적용 시 초기화가 안된다. Default 어노테이션으로 초기화해주어야 함.
    @Builder.Default
    private List<CommentEntity> comments = new ArrayList<>();


    // test 코드용
    public static PostDTO toDTO(PostEntity postEntity){
        return PostDTO.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .writeDate(postEntity.getWriteDate().toString())
                .like(postEntity.getLikes())
                .views(postEntity.getViews())
                .comments(CommentEntity.toDTOs(CommentEntity.descByWriteDateComment(postEntity.getComments())))
                .email(postEntity.getUser().getEmail()).build();
    }

    public static ResponsePostDTO toResponseDTO(PostEntity postEntity, Predicate<CommentEntity> predicate){
        List<CommentEntity> comments = postEntity.getComments()
                .stream().filter(predicate).collect(Collectors.toList());

        return ResponsePostDTO.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .writeDate(postEntity.getWriteDate().toString())
                .like(postEntity.getLikes())
                .views(postEntity.getViews())
                .comments(
                        CommentEntity.toResponseCommentDTOs(CommentEntity.descByWriteDateComment(comments)))
                .user(UserEntity.toDTO(postEntity.getUser())).build();
    }

    public static List<ResponsePostDTO> toResponseDTOs(List<PostEntity> postEntities, Predicate<CommentEntity> predicate){
        List<ResponsePostDTO> DTOs = new ArrayList<>();
        postEntities.forEach(entity -> DTOs.add(toResponseDTO(entity, predicate)));
        return DTOs;
    }

    public static PostEntity updateEntity(PostEntity postEntity, PostDTO postDTO){
        return PostEntity.builder()
                // 변경되지 않는
                .id(postEntity.getId())
                .views(postEntity.getViews())
                .likes(postEntity.getLikes())
                .writeDate(postEntity.getWriteDate())
                .user(postEntity.getUser())
                .comments(postEntity.getComments())

                // 변경되는 값
                .content(postDTO.getContent())
                .title(postDTO.getTitle())
                .category(postDTO.getCategory())
                .build();
    }

}
