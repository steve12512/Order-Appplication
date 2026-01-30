package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.dto.CreateUserRequest;
import com.example.demo.dto.UpdateUserEmailRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.dto.UserSearchRequest;
import com.example.demo.exception.InvalidIdException;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Parameter;
import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getAge(), "Successfully retrived user");
    }
// i ve replaced this with its Pageable version below
//    @GetMapping
//    public List<UserResponse> getAllUsers() {
//        List<User> users = userService.getUsers();
//        return users.stream().map(user -> new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getAge())).toList();
//    }

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
                new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getAge()));
    }

    @GetMapping("/filter/search")
    public Page<UserResponse> getUsersOlderThan(
    @Valid @ModelAttribute UserSearchRequest request
    )
    {
        System.out.println("in the getUsersOlderThan function");
        Pageable pageable = PageRequest.of(request.page(),request.size(),Sort.by(request.sortBy()));
        Page<User> users = (request.username().isEmpty())
                ?userService.getUsersOlderThan(request.minAge(), pageable)
                :userService.getUsersAgeGreaterThanEqualAndUsernameContaining(request.minAge(),request.username(),pageable);
        return users.map(user
                -> new UserResponse
                (user.getId(),user.getUsername(),user.getEmail(),user.getAge(),"Successfully retrived user"));
    }


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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }

}

