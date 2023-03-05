package dev.jlkeesh.lesson_1_rest_api.dto.auth;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterDTO {
    private String email;
    private String username;
    private String password;
}
