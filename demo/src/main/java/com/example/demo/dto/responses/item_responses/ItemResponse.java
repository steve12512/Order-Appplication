package com.example.demo.dto.responses.item_responses;


public record ItemResponse(
        int id,
        String name,
        Double price,
        String info,
        String message
) {
}