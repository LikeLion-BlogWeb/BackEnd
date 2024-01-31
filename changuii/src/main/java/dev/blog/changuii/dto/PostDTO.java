package dev.blog.changuii.dto;


import dev.blog.changuii.entity.PostEntity;
import lombok.*;

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
    private String title;
    private String content;
    private String email;
    private String writeDate;
    @Builder.Default
    private List<String> like = new ArrayList<>();
    private Long views;


    public static PostDTO entityToDTO(PostEntity postEntity){
        return PostDTO.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .email(postEntity.getEmail())
                .writeDate(postEntity.getWriteDate().toString())
                .like(postEntity.getLikes())
                .views(postEntity.getViews()).build();
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
