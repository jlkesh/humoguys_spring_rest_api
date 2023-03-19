package dev.jlkeesh.lesson_1_rest_api.domain;


import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Blog extends Auditable{
    private String title;
    private String content;

    @Builder(builderMethodName = "childBuilder")
    public Blog(String id, LocalDateTime createdAt, boolean deleted, String title, String content) {
        super(id, createdAt, deleted);
        this.title = title;
        this.content = content;
    }
}
