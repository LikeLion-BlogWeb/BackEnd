package dev.blog.changuii.dto;


import dev.blog.changuii.entity.CommentEntity;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotBlank(message = "댓글이 작성될 게시글 ID는 필수 값입니다.")
    @NotNull(message = "댓글이 작성될 게시글 ID는 필수 값입니다.")
    private Long postId;


    public static CommentDTO EntityToDTO(CommentEntity comment){
        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .writeDate(comment.getWriteDate().toString())
                .email(comment.getUser().getEmail())
                .postId(comment.getPost().getId())
                .build();
    }

    public static List<CommentDTO> EntityListToDTOList(List<CommentEntity> comments){

        List<CommentDTO> commentDTOList = new ArrayList<>();
        comments.forEach(
                (entity) -> {
            commentDTOList.add(EntityToDTO(entity)); });
        return commentDTOList;
    }
}
