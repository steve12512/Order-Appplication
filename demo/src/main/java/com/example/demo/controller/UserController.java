package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.dto.requests.user_requests.*;
import com.example.demo.dto.responses.user_responses.UserResponse;
import com.example.demo.dto.requests.user_requests.UserSearchRequest;
import com.example.demo.dto.requests.user_requests.UserSummaryRequest;
import com.example.demo.dto.responses.user_responses.UserSummaryResponse;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getAge(),user.getIsActive(), "Successfully retrived user");
    }


    @GetMapping
    public Page<UserResponse> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy

    )
    {
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy));
        return userService.getUsers(pageable).
                map(user ->
                new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getAge(), user.getIsActive(),"Successfully retrieved user"));
    }

    @GetMapping("/filter/search")
    public Page<UserResponse> getUsersOlderThan(
    @Valid @ModelAttribute UserSearchRequest request) {
        Pageable pageable = PageRequest.of(request.page(), request.size(), Sort.by(request.sortBy()));
        Page<User> users = userService.getUsersAgeGreaterThanEqual(request, pageable);
        return users.map(user
                -> new UserResponse
                (user.getId(),user.getUsername(),user.getEmail(),user.getAge(),"Successfully retrived user"));
    }

    @GetMapping("filter/advanced_search")
    public Page<UserSummaryResponse> getRankedUsers(UserSummaryRequest request){
        Page<User> users = userService.getRankedUsers(request);
        return users.map(user -> new UserSummaryResponse(user.getId(), user.getUsername(), user.getAge(), user.getIsActive()));
    }
//{{baseUrl}}/users/filter/advanced_search/?minAge=30&maxAge=50&usernameContains=ccc&isActive=true&size=10&size=20








    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest request) {
        User user = userService.createUser(request.getAge(), request.getUsername(), request.getEmail());
        UserResponse response = new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getAge(), "User has successfully been created");
        return response;
    }

    @PutMapping("/{id}/email")
    public UserResponse updateUserEmail(@PathVariable Long id, @Valid @RequestBody UpdateUserEmailRequest request) {
        User user = userService.updateUserEmail(id, request.getEmail());
        UserResponse userResponse = new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getAge(), "Successfully updated the user's email to : " + user.getEmail());
        return userResponse;
    }


    @PutMapping("/{id}/isActive")
    public UserResponse setUsertoInactive(@PathVariable Long id){
        User user = userService.setUserStatusToInactive(id);
        return new UserResponse(id,user.getUsername(),user.getEmail(),user.getAge(),user.getIsActive(),"successfully set the user s status to inactive");
    }









    @DeleteMapping("/{id}")
    public UserResponse deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return new UserResponse("User with id: " + id + " has been successfully deleted");
    }

}

