package dev.blog.changuii.dto;


import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDTO {

    @NotNull(message = "email null")
    @NotBlank(message = "email 값이 없습니다.")
    @Email(message = "email의 형식이 잘못되었습니다.")
    private String email;

    @NotNull(message = "password null")
    @NotBlank(message = "password 값이 없습니다.")
    @Size(min = 8, message = "password는 최소 8자리가 만족되어야 합니다.")
    private String password;

    @NotNull(message = "이름이 없습니다.")
    private String name;

}
