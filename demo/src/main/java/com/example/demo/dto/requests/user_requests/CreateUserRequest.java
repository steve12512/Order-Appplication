package com.example.demo.dto.requests.user_requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateUserRequest {
  @NotBlank String username;
  @Email String email;
  @NotNull int age;

  public int getAge() {
    return age;
  }

  // $ curl -X POST http://localhost:8080/users -H "Content-Type: application/json" -d '{"id":1,
  // "username": "Jannis", "email": "jannis_lag@sarantaporo.gr", "age" : 30}'

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }
}
