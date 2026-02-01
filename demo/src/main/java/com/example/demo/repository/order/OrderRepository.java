package com.example.demo.repository.order;

import com.example.demo.domain.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findById(Long id);



//    @Query(value = " SELECT O.* FROM ORDERS O JOIN USERS U ON U.ID = O.USER_ID " +
//            " WHERE U.ID = :userId AND LOWER(U.USERNAME) LIKE LOWER(CONCAT('%',:userName,'%'))",
//    nativeQuery = true)

//NATIVE VERSION ABOVE, I have to add the pageable var!

    @Query("SELECT o FROM Order o JOIN o.user u  WHERE u.id = :userId " +
            " AND LOWER(u.username) LIKE LOWER(CONCAT('%',:userName,'%'))")
    Page<Order> findByIdAndUsernameContaining(
            @Param(value = "userId") Long userId,
            @Param(value = "userName") String userName, Pageable pageable);
}