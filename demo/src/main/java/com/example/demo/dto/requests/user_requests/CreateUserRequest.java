package com.example.demo.dto.requests.user_requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateUserRequest {
    @NotBlank
    String username;
    @Email
    String email;
    @NotNull
    int age;

    public int getAge() {
        return age;
    }

    //$ curl -X POST http://localhost:8080/users -H "Content-Type: application/json" -d '{"id":1, "username": "Jannis", "email": "jannis_lag@sarantaporo.gr", "age" : 30}'

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public CreateUserRequest(String username, String email, int age) {
        this.username = username;
        this.email = email;
        this.age = age;
    }
}