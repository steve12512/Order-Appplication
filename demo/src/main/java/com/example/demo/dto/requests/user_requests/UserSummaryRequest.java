package com.example.demo.dto.requests.user_requests;

import org.springframework.data.domain.Pageable;

public record UserSummaryRequest(
     Integer minAge,
     Integer maxAge,
     String usernameContains,
     Boolean isActive,
     Pageable pageable
)
        {}