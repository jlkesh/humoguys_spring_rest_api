package dev.jlkeesh.lesson_1_rest_api.controller.auth;


import dev.jlkeesh.lesson_1_rest_api.dto.auth.UserRegisterDTO;
import dev.jlkeesh.lesson_1_rest_api.service.AuthUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {


    private final AuthUserService authUserService;

    public AuthController(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDTO dto) {
        return new ResponseEntity<>(authUserService.save(dto), HttpStatus.CREATED);
    }

    @GetMapping("/activate/{code}")
    public ResponseEntity<Void> activate(@PathVariable String code) {
        authUserService.activate(code);
        return ResponseEntity.noContent().build();
    }
}
