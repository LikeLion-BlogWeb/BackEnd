package dev.blog.changuii.dto.response;


import dev.blog.changuii.dto.CommentDTO;
import dev.blog.changuii.dto.UserDTO;
import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResponsePostDTO {

    private long id;
    private String title;
    private String content;
    private String writeDate;

    @Builder.Default
    private List<String> like = new ArrayList<>();

    private long views;

    private String category;

    // 단일 게시글 조회시 댓글 정보도 같이 반환
    @Builder.Default
    private List<ResponseCommentDTO> comments = new ArrayList<>();

    private UserDTO user;


}
