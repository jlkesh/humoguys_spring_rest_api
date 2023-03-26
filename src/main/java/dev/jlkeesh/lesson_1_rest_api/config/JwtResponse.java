package dev.jlkeesh.lesson_1_rest_api.config;

import java.util.List;

public class JwtResponse {
    private final String jwt;
    private final String username;
    private final List<String> roles;

    public JwtResponse(String jwt, String username, List<String> roles) {
        this.jwt = jwt;
        this.username = username;
        this.roles = roles;
    }

    public String getJwt() {
        return jwt;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }
}
