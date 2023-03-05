package dev.jlkeesh.lesson_1_rest_api.repository;

import dev.jlkeesh.lesson_1_rest_api.domain.auth.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthUserRepository extends JpaRepository<AuthUser, String> {
}