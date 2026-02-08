package com.example.demo.controller;

import com.example.demo.dto.requests.order_requests.CreateOrderRequest;
import com.example.demo.dto.requests.order_requests.ModifyOrderRequest;
import com.example.demo.dto.requests.order_requests.SearchOrdersByUserNameRequest;
import com.example.demo.dto.responses.order_responses.OrderResponse;
import com.example.demo.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@Validated
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping("/{id}")
  @Validated
  public OrderResponse getOrder(@PathVariable @Validated Long id) {
    return orderService.findById(id);
  }

  @GetMapping("/usernameSearch")
  @Validated
  public Page<OrderResponse> getOrdersByUserIdAndNameContaining(
      @Valid @ModelAttribute SearchOrdersByUserNameRequest request) {
    Pageable pageable =
        PageRequest.of(request.pageNumber(), request.pageSize(), Sort.by(request.sortBy()));
    return orderService.searchByUserIdAndNameContaining(
        request.userId(), request.username(), pageable);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public OrderResponse createOrder(@RequestBody @Validated CreateOrderRequest request) {
    return orderService.createOrder(request);
  }

  @PutMapping("/cancel")
  @Valid
  public OrderResponse cancelOrder(@RequestBody @Validated ModifyOrderRequest request) {
    return orderService.cancelOrder(request);
  }

  @PutMapping("/complete")
  @Valid
  public OrderResponse completeOrder(@RequestBody @Validated ModifyOrderRequest request) {
    return orderService.completeOrder(request);
  }
}
