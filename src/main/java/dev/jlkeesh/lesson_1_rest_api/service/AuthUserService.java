package dev.jlkeesh.lesson_1_rest_api.service;

import dev.jlkeesh.lesson_1_rest_api.domain.auth.AuthRole;
import dev.jlkeesh.lesson_1_rest_api.domain.auth.AuthUser;
import dev.jlkeesh.lesson_1_rest_api.dto.auth.UserRegisterDTO;
import dev.jlkeesh.lesson_1_rest_api.mappers.AuthUserMapper;
import dev.jlkeesh.lesson_1_rest_api.repository.AuthRoleRepository;
import dev.jlkeesh.lesson_1_rest_api.repository.AuthUserRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record AuthUserService(AuthUserRepository authUserRepository,
                              AuthRoleRepository authRoleRepository,
                              AuthUserMapper mapper) {
    public String save(@NonNull UserRegisterDTO dto) {
        AuthRole authRole = authRoleRepository.findByCode("USER")
                .orElseThrow(() -> new RuntimeException("Role Not Found By Code 'USER'"));
        AuthUser authUser = mapper.fromCreateDto(dto);
        /*authUser.setPassword();*/
        authUser.setRoles(List.of(authRole));
        authUserRepository.save(authUser);
        return authUser.getId();
    }
}
