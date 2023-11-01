package dev.blog.changuii.dto;


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


}
