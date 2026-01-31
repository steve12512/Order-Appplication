package com.example.demo.dto.requests.item_requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateItemRequest(
        @NotBlank
        String name,
        @NotNull
        Double price,
        String info
) {
}