package dev.germane.customer;

import java.util.List;

public record CustomerDTO(
        Long id,
        String name,
        String email,
        Gender gender,
        Integer age,
        List<String> roles,
        String username
) {
}