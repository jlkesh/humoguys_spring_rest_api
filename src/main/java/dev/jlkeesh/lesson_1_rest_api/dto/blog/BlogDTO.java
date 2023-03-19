package dev.jlkeesh.lesson_1_rest_api.dto.blog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogDTO {
    private String id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}
