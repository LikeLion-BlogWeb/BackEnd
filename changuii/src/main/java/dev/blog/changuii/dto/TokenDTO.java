package dev.blog.changuii.dto;


import dev.blog.changuii.entity.UserEntity;
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

    public static TokenDTO makeTokenDTO(String email, String token){
        return TokenDTO.builder()
                .email(email)
                .token(token)
                .build();
    }

}
