package com.example.demo.repository;

import com.example.demo.domain.User;
import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    List<User> findAll();
    Page<User> findAll(Pageable pageable);
    Page<User> findByAgeGreaterThanEqual(int minAge, Pageable pageable);
    Page <User> findByAgeGreaterThanEqualAndUsernameContaining(int minAge, String name, Pageable pageable);



    @Query("SELECT u " + "FROM User u " + "WHERE u.age > :minAge " +
            " AND u.age < :maxAge" +
            " AND u.username like lower(concat('%',:usernameContains,'%'))" +
            " AND ( ( u.isActive = :isActive) OR (:isActive is null) )"
    )
    Page<User> findRankedUsers(@Param("minAge") int minAge,
                               @Param("maxAge") int maxAge,
                               @Param("usernameContains") String usernameContains,
                               @Param("isActive") Boolean isActive,
                               Pageable pageable
    );

    void deleteById(Long id);
    User save(User user);

}