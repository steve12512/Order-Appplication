package com.example.demo.controller;

import com.example.demo.domain.Order;
import com.example.demo.domain.OrderItem;
import com.example.demo.dto.requests.order_requests.CreateOrderRequest;
import com.example.demo.dto.responses.order_responses.OrderResponse;
import com.example.demo.dto.responses.user_responses.UserResponse;
import com.example.demo.service.OrderService;
import jakarta.validation.Valid;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/orders")
@Validated
public class OrderController{

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    @Validated
    public OrderResponse getOrder(@PathVariable @Validated Long id) {
        Order order = orderService.findById(id);
        return  new OrderResponse(order.getId(),order.getUser().getId(),order.getOrderItems().stream().map(item -> item.getId()).toList(),order.getUser().getUsername(),order.getCreated_at());
    }




    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody @Validated CreateOrderRequest request){
        Order order = orderService.createOrder(request);
        return new OrderResponse(order.getId(),order.getUser().getId(),order.getOrderItems().stream().map(u -> u.getId()).toList(),order.getUser().getUsername(),order.getCreated_at());
    }


}