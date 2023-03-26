package dev.jlkeesh.lesson_1_rest_api.repository;

import dev.jlkeesh.lesson_1_rest_api.domain.auth.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, String> {
    Optional<AuthUser> findByUsername(String username);
}