package dev.jlkeesh.lesson_1_rest_api.domain.auth;


import dev.jlkeesh.lesson_1_rest_api.domain.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthUser extends Auditable {

    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    private String profilePicture;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "auth_user_auth_role_table",
            joinColumns = @JoinColumn(name = "auth_user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "auth_role_id", referencedColumnName = "id")
    )
    private List<AuthRole> roles;

    @Builder(builderMethodName = "childBuilder")
    public AuthUser(String id, LocalDateTime createdAt, boolean deleted, String email, String username, String password, String profilePicture, List<AuthRole> roles) {
        super(id, createdAt, deleted);
        this.email = email;
        this.username = username;
        this.password = password;
        this.profilePicture = profilePicture;
        this.roles = roles;
    }
}
