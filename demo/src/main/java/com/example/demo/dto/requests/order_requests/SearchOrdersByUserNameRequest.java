package com.example.demo.dto.requests.order_requests;

public record SearchOrdersByUserNameRequest(
        Long userId,
        String username,
        Integer pageSize,
        Integer pageNumber,
        String sortBy

) {
    public SearchOrdersByUserNameRequest {
        if(pageSize ==null) pageSize =10;
        if (pageNumber == null) pageNumber = 0;
        if (sortBy == null) sortBy ="id";
    }


}