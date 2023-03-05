package dev.jlkeesh.lesson_1_rest_api.domain.auth;

import dev.jlkeesh.lesson_1_rest_api.domain.Auditable;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AuthRole extends Auditable {
    private String name;
    private String code;

    @Builder(builderMethodName = "childBuilder")
    public AuthRole(String id, LocalDateTime createdAt, boolean deleted, String name, String code) {
        super(id, createdAt, deleted);
        this.name = name;
        this.code = code;
    }
}
