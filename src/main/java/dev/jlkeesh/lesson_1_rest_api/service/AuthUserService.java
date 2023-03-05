package dev.jlkeesh.lesson_1_rest_api.service;

import dev.jlkeesh.lesson_1_rest_api.domain.auth.AuthRole;
import dev.jlkeesh.lesson_1_rest_api.domain.auth.AuthToken;
import dev.jlkeesh.lesson_1_rest_api.domain.auth.AuthUser;
import dev.jlkeesh.lesson_1_rest_api.dto.auth.UserRegisterDTO;
import dev.jlkeesh.lesson_1_rest_api.mappers.AuthUserMapper;
import dev.jlkeesh.lesson_1_rest_api.repository.AuthRoleRepository;
import dev.jlkeesh.lesson_1_rest_api.repository.AuthTokenRepository;
import dev.jlkeesh.lesson_1_rest_api.repository.AuthUserRepository;
import dev.jlkeesh.lesson_1_rest_api.utils.MailService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public record AuthUserService(AuthUserRepository authUserRepository,
                              AuthRoleRepository authRoleRepository,
                              AuthTokenRepository authTokenRepository,

                              MailService mailService,
                              AuthUserMapper mapper) {

    public String save(@NonNull UserRegisterDTO dto) {
        AuthRole authRole = authRoleRepository.findByCode("USER")
                .orElseThrow(() -> new RuntimeException("Role Not Found By Code 'USER'"));
        AuthUser authUser = mapper.fromCreateDto(dto);
        authUser.setRoles(List.of(authRole));
        authUser.setStatus(AuthUser.Status.NOT_ACTIVATED);
        authUserRepository.save(authUser);
        AuthToken authToken = AuthToken.childBuilder()
                .code(UUID.randomUUID().toString())
                .validTill(LocalDateTime.now().plusMinutes(5))
                .userID(authUser.getId())
                .build();
        authTokenRepository.save(authToken);
        mailService.sendActivationMail(Map.of("from", "from@gmail.com",
                "to", "to@gmail.com",
                "subject", "Test For Spring Boot Mail",
                "text", "Mail body",
                "code", authToken.getCode()
        ));

        return authUser.getId();
    }

    public void activate(String code) {
        AuthToken token = authTokenRepository.findByCodeAndDeletedIsFalse(code)
                .orElseThrow(() -> new RuntimeException("AuthToken not found"));
        if (token.getValidTill().isBefore(LocalDateTime.now())) {
            token.setDeleted(true);
            authTokenRepository.save(token);
            throw new RuntimeException("Token expired");
        }

        AuthUser authUser = authUserRepository.findById(token.getUserID())
                .orElseThrow(() -> new RuntimeException("AuthUser not found"));
        authUser.setStatus(AuthUser.Status.ACTIVE);
        authUserRepository.save(authUser);
    }
}
