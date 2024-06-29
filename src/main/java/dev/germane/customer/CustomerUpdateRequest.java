package dev.germane.customer;

public record CustomerUpdateRequest(
        String name,
        String email,
        Integer age
) {
}
