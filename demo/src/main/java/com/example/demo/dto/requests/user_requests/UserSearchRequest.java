package com.example.demo.dto.requests.user_requests;

import jakarta.validation.constraints.Min;

public record UserSearchRequest(

    Integer page,
    Integer size,
    String sortBy,
    @Min(18)
    Integer minAge,
    String username

) {
    public  UserSearchRequest{
        if (page == null) page =0;
        if (size == null) size = 10;
        if (sortBy == null) sortBy = "id";
        if (username == null) username = "";
    }

    @Override
    public Integer page() {
        return page;
    }

    @Override
    public Integer size() {
        return size;
    }

    @Override
    public String sortBy() {
        return sortBy;
    }

    @Override
    public Integer minAge() {
        return minAge;
    }

    @Override
    public String username() {
        return username;
    }
}