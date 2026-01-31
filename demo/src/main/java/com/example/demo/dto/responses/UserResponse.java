package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserResponse {
    Long id;
    String username;
    String email;
    int age;
    String message;
    boolean isActive;

    public UserResponse(Long id, String username, String email, String s) {
    }

    public String getMessage() {
        return message;
    }

    public UserResponse(Long id, String username, String email, int age, String message) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.age = age;
        this.message = message;
    }

    public UserResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getAge() {
        return age;
    }

    public UserResponse(Long id, String username, String email, int age, boolean isActive, String message) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.age = age;
        this.isActive = isActive;
        this.message = message;
    }

    public UserResponse(String message) {
        this.message = message;
    }


}