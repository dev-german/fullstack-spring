package dev.germane.auth;

public record AuthenticationRequest(
        String username,
        String password
) {
}
