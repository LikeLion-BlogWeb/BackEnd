package dev.blog.changuii.dto;
import dev.blog.changuii.entity.PostEntity;
import lombok.*;
import org.hibernate.validator.constraints.*;

import javax.validation.constraints.NotNull;
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


    public static PostDTO entityToDTO(PostEntity postEntity){
        return PostDTO.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .email(postEntity.getUser().getEmail())
                .writeDate(postEntity.getWriteDate().toString())
                .like(postEntity.getLikes())
                .views(postEntity.getViews())
                .category(postEntity.getCategory()).build();
    }
    public static List<PostDTO> entityListToDTOList(List<PostEntity> postEntityList){
        List<PostDTO> postDTOList = new ArrayList<>();
        for(PostEntity postEntity : postEntityList){
            postDTOList.add(
                    PostDTO.entityToDTO(postEntity)
            );
        }

        return postDTOList;
    }

}
