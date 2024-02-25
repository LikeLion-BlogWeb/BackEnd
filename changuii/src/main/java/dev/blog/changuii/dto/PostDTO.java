package dev.blog.changuii.dto;
import dev.blog.changuii.entity.PostEntity;
import dev.blog.changuii.entity.UserEntity;
import lombok.*;
import org.hibernate.validator.constraints.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private Long id;

    @NotNull(message = "게시글 제목이 없습니다.")
    @NotBlank(message = "게시글 제목이 없습니다.")
    private String title;

    @NotNull(message = "게시글 내용이 없습니다.")
    @NotBlank(message = "게시글 내용이 없습니다.")
    private String content;

    @NotNull(message = "작성자 이메일은 필수 값입니다.")
    @NotBlank(message = "작성자 이메일은 필수 값입니다.")
    @Email(message = "작성자 이메일의 형식이 잘못되었습니다.")
    private String email;


    @NotBlank(message = "작성 시각은 필수 값입니다.")
    @NotNull(message = "작성 시각은 필수 값입니다.")
    private String writeDate;

    @Builder.Default
    private List<String> like = new ArrayList<>();

    private Long views;

    private String category;

    // 단일 게시글 조회시 댓글 정보도 같이 반환
    @Builder.Default
    private List<CommentDTO> comments = new ArrayList<>();

    public static PostEntity toEntity(PostDTO postDTO, UserEntity user){
        return PostEntity.builder()
                .content(postDTO.getContent())
                .user(user)
                .title(postDTO.getTitle())
                .writeDate(LocalDateTime.parse(postDTO.getWriteDate()))
                .likes(postDTO.getLike())
                .views(postDTO.getViews())
                .category(postDTO.getCategory())
                .build();
    }

}
