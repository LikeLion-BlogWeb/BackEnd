package dev.blog.changuii.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TokenDTO {

    private String token;
    private String email;
}
