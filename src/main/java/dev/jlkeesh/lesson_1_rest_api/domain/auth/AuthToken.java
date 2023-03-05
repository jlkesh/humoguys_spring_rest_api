package dev.jlkeesh.lesson_1_rest_api.domain.auth;

import dev.jlkeesh.lesson_1_rest_api.domain.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Future;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AuthToken extends Auditable {

    @Column(nullable = false, unique = true)
    private String code;
    @Column(nullable = false)
    private String userID;
    @Column(nullable = false)
    private LocalDateTime validTill;

    @Builder(builderMethodName = "childBuilder")

    public AuthToken(String id, LocalDateTime createdAt, boolean deleted, String code, String userID, LocalDateTime validTill) {
        super(id, createdAt, deleted);
        this.code = code;
        this.userID = userID;
        this.validTill = validTill;
    }
}
