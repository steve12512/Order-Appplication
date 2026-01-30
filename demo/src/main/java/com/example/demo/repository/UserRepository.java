package com.example.demo.repository;

import com.example.demo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(int id);
    List<User> findAll();
    Page<User> findAll(Pageable pageable);
    Page<User> findByAgeGreaterThanEqual(int minAge, Pageable pageable);
    Page <User> findByAgeGreaterThanEqualAndUsernameContaining(int minAge, String name, Pageable pageable);
    void deleteById(int id);
    User save(User user);

}