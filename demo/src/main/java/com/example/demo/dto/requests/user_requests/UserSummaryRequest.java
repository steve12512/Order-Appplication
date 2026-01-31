package com.example.demo.dto.requests;

import org.springframework.data.domain.Pageable;

public record UserSummaryRequest(
     Integer minAge,
     Integer maxAge,
     String usernameContains,
     Boolean isActive,
     Pageable pageable
)
        {}