package com.example.demo.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;

public class UpdateUserEmailRequest {
    @Email
    String email;

    public String getEmail() {
        return email;
    }

    public UpdateUserEmailRequest(String email) {
        this.email = email;
    }
}