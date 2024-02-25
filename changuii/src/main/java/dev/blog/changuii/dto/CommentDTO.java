package dev.blog.changuii.dto;


import dev.blog.changuii.entity.CommentEntity;
import dev.blog.changuii.entity.PostEntity;
import dev.blog.changuii.entity.UserEntity;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {


    private Long id;

    @NotBlank(message = "댓글 내용은 필수 값입니다.")
    @NotNull(message = "댓글 내용은 필수 값입니다.")
    private String content;

    @NotNull(message = "작성 시각은 필수 값입니다.")
    @NotBlank(message = "작성 시각은 필수 값입니다.")
    private String writeDate;

    @NotBlank(message = "작성자는 필수 값입니다.")
    @NotNull(message = "작성자는 필수 값입니다.")
    @Email
    private String email;

    // NotBlank는 String에만 가능
    @NotNull(message = "댓글이 작성될 게시글 ID는 필수 값입니다.")
    private Long postId;

    public static CommentEntity toEntity(CommentDTO commentDTO, UserEntity user, PostEntity post){
        return CommentEntity.builder()
                .content(commentDTO.getContent())
                .writeDate(LocalDateTime.parse(commentDTO.getWriteDate()))
                .post(post)
                .user(user)
                .build();
    }



}
