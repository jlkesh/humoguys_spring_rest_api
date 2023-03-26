package dev.jlkeesh.lesson_1_rest_api.controller.auth;


import dev.jlkeesh.lesson_1_rest_api.config.JwtResponse;
import dev.jlkeesh.lesson_1_rest_api.config.JwtUtils;
import dev.jlkeesh.lesson_1_rest_api.domain.auth.AuthRole;
import dev.jlkeesh.lesson_1_rest_api.domain.auth.AuthUser;
import dev.jlkeesh.lesson_1_rest_api.dto.auth.LoginRequest;
import dev.jlkeesh.lesson_1_rest_api.dto.auth.UserRegisterDTO;
import dev.jlkeesh.lesson_1_rest_api.repository.AuthRoleRepository;
import dev.jlkeesh.lesson_1_rest_api.repository.AuthUserRepository;
import dev.jlkeesh.lesson_1_rest_api.service.AuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUserService authUserService;
    private final AuthenticationManager authenticationManager;
    private final AuthUserRepository authUserRepository;
    private final AuthRoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getUsername(),
                roles));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDTO dto) {
        Optional<AuthUser> authUserOptional = authUserRepository.findByUsername(dto.getUsername());
        if (!authUserOptional.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }
        // TODO: 26/03/23 email ni ham check qilish kerak
        AuthRole authRole = AuthRole.childBuilder()
                .code("USER")
                .name("User")
                .build();
        AuthUser authUser = AuthUser.childBuilder()
                .username(dto.getUsername())
                .password(encoder.encode(dto.getPassword()))
                .roles(List.of(authRole))
                .email(dto.getEmail())
                .build();
        authUserRepository.save(authUser);
        return ResponseEntity.ok("User registered successfully!");
    }
}
