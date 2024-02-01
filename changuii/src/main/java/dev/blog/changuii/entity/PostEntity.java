package dev.blog.changuii.entity;

import dev.blog.changuii.dto.PostDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String email;

    @Column
    private LocalDateTime writeDate;

    // SQL 예약어와 동일한 컬럼 사용불가 !!
    @Column
    @Builder.Default
    @ElementCollection
    private List<String> likes = new ArrayList<>();

    @Column
    private Long views;


    public static PostEntity DtoToEntity(PostDTO postDTO){
        return PostEntity.builder()
                .content(postDTO.getContent())
                .email(postDTO.getEmail())
                .title(postDTO.getTitle())
                .writeDate(LocalDateTime.parse(postDTO.getWriteDate()))
                .likes(postDTO.getLike())
                .views(postDTO.getViews()).build();
    }

    public static PostEntity initEntity(PostDTO postDTO){
        return PostEntity.builder()
                .content(postDTO.getContent())
                .email(postDTO.getEmail())
                .title(postDTO.getTitle())
                .writeDate(LocalDateTime.parse(postDTO.getWriteDate()))
                .likes(new ArrayList<>())
                .views(0L).build();
    }

    public static PostEntity updateEntity(PostEntity postEntity, PostDTO postDTO){

        return PostEntity.builder()
                // 변경되지 않는
                .id(postEntity.getId())
                .views(postEntity.getViews())
                .likes(postEntity.getLikes())
                .writeDate(postEntity.getWriteDate())
                .email(postEntity.getEmail())

                // 변경되는 값
                .content(postDTO.getContent())
                .title(postDTO.getTitle())
                .build();
    }

}
