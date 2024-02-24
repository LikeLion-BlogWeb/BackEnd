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
    private String name;

    public static TokenDTO makeTokenDTO(UserEntity user, String token){
        return TokenDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .token(token)
                .build();
    }

}
