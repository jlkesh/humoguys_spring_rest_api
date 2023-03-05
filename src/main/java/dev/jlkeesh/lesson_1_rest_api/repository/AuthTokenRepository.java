package dev.jlkeesh.lesson_1_rest_api.repository;

import dev.jlkeesh.lesson_1_rest_api.domain.auth.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthTokenRepository extends JpaRepository<AuthToken, String> {
    Optional<AuthToken> findByCodeAndDeletedIsFalse(String code);
}