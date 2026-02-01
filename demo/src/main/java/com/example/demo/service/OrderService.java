package com.example.demo.service;

import com.example.demo.domain.Item;
import com.example.demo.domain.Order;
import com.example.demo.domain.OrderItem;
import com.example.demo.domain.User;
import com.example.demo.dto.requests.order_item_requests.OrderItemRequest;
import com.example.demo.dto.requests.order_requests.CreateOrderRequest;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.order.OrderNotFoundException;
import com.example.demo.repository.order.OrderRepository;
import com.example.demo.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository  orderRepository;
    private final UserRepository userRepository;
    private final ItemService itemService;


    @Transactional
    public Order findById(Long id){
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Transactional
    public Page<Order> searchByUserIdAndNameContaining(Long userId, String userName, Pageable pageable){
        Page<Order> orders = orderRepository.findByIdAndUsernameContaining(userId,userName,pageable);
        if (orders.isEmpty() ) throw  new OrderNotFoundException(userId);
        return  orders;
    }








    @Transactional
    public Order createOrder(CreateOrderRequest request){
        User user = userRepository.findById(request.userId()).orElseThrow(() -> new UserNotFoundException(request.userId()));
        Order order = new Order();
        order.setUser(user);

        mapRequestToEntity(request, order);
        orderRepository.save(order);
        return order;
    }

    private void mapRequestToEntity(CreateOrderRequest request, Order order ){
        for (OrderItemRequest orderItemRequest : request.orderItemRequests()){
            Item item =itemService.getItemById(orderItemRequest.itemId());
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setItem(item);
            orderItem.setInformation(request.information());
            order.getOrderItems().add(orderItem);

        }
    }



}