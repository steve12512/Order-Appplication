package com.example.demo.dto.responses;

public record UserSummaryResponse(
        Long id,
        String username,
        Integer age,
        Boolean isActive

) {
}