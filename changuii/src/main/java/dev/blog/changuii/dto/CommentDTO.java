package dev.blog.changuii.dto;


import dev.blog.changuii.entity.CommentEntity;
import lombok.*;

import javax.validation.constraints.Email;
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

    private String content;
    private String writeDate;
    @Email
    private String email;

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
