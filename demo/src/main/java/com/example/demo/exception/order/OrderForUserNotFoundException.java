package com.example.demo.exception.order;

public class OrderForUserNotFoundException extends RuntimeException{
    Long userId;

    public Long getUserId() {
        return userId;
    }

    public OrderForUserNotFoundException(Long userId) {
        super("Order for user with id: " + userId + " not found");
    }
}