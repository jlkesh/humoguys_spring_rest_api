package dev.jlkeesh.lesson_1_rest_api.repository;

import dev.jlkeesh.lesson_1_rest_api.domain.auth.AuthRole;
import org.springframework.data.jpa.mapping.JpaPersistentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AuthRoleRepository extends JpaRepository<AuthRole, String> {
    Optional<AuthRole> findByCode(String code);
}
