package dev.blog.changuii.dto.response;


import dev.blog.changuii.dto.UserDTO;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCommentDTO {

    private Long id;
    private String content;
    private String writeDate;
    private UserDTO user;

    // NotBlank는 String에만 가능
    @NotNull(message = "댓글이 작성될 게시글 ID는 필수 값입니다.")
    private Long postId;
}
