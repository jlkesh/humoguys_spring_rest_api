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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public record AuthUserService(AuthUserRepository authUserRepository,
                              AuthRoleRepository authRoleRepository,
                              AuthTokenRepository authTokenRepository,

                              MailService mailService,
                              AuthUserMapper mapper) implements UserDetailsService {

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        List<GrantedAuthority> authorities = new ArrayList<>();
        Objects.requireNonNullElse(authUser.getRoles(), List.<AuthRole>of())
                .forEach(r -> {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + r.getCode()));
                });
        return new User(authUser.getUsername(), authUser.getPassword(), authorities);
    }
}
