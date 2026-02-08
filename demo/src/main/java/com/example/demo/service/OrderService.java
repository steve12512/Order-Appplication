package com.example.demo.service;

import com.example.demo.domain.Item;
import com.example.demo.domain.Order;
import com.example.demo.domain.OrderItem;
import com.example.demo.domain.User;
import com.example.demo.dto.requests.order_item_requests.OrderItemRequest;
import com.example.demo.dto.requests.order_requests.CreateOrderRequest;
import com.example.demo.dto.requests.order_requests.ModifyOrderRequest;
import com.example.demo.dto.responses.order_responses.OrderResponse;
import com.example.demo.exception.item.InsufficientItemQuantityException;
import com.example.demo.exception.order.OrderNotFoundException;
import com.example.demo.exception.order.WrongOrderUserIDException;
import com.example.demo.repository.order.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;
  private final UserService userService;
  private final ItemService itemService;

  @Transactional
  public OrderResponse findById(Long id) {
    Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    return new OrderResponse(
        order.getId(),
        order.getUser().getId(),
        order.getOrderItems().stream().map((item) -> item.getId()).toList(),
        order.getUser().getUsername(),
        order.getCreated_at());
  }

  @Transactional
  public Page<OrderResponse> searchByUserIdAndNameContaining(
      Long userId, String userName, Pageable pageable) {
    Page<Order> orders = orderRepository.findByIdAndUsernameContaining(userId, userName, pageable);
    if (orders.isEmpty()) throw new OrderNotFoundException(userId);
    return orders.map(
        order ->
            new OrderResponse(
                order.getUser().getId(),
                order.getId(),
                order.getOrderItems().stream().map(item -> item.getId()).toList(),
                order.getUser().getUsername(),
                order.getCreated_at()));
  }

  @Transactional
  public OrderResponse createOrder(CreateOrderRequest request) {
    User user = userService.findUserById(request.userId());
    Order order = new Order();
    order.setUser(user);
    addOrderItemsToOrder(request, order);
    orderRepository.save(order);
    return new OrderResponse(
        order.getId(),
        order.getUser().getId(),
        order.getOrderItems().stream().map(u -> u.getId()).toList(),
        order.getUser().getUsername(),
        order.getCreated_at());
  }

  private void addOrderItemsToOrder(CreateOrderRequest request, Order order) {
    for (OrderItemRequest orderItemRequest : request.orderItemRequests()) {
      Item item = itemService.getItemById(orderItemRequest.itemId());
      if (itemHasAvailableQuantity(item, orderItemRequest.quantity())) {
        updateItemQuantity(item, orderItemRequest.quantity());
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setItem(item);
        orderItem.setInformation(request.information());
        order.getOrderItems().add(orderItem);
      }
    }
  }

  private boolean itemHasAvailableQuantity(Item item, Integer quantity) {
    if (item.getAvailableQuantity() < quantity)
      throw new InsufficientItemQuantityException(
          "Item :"
              + item.getName()
              + " has available quantity "
              + item.getAvailableQuantity()
              + " but the order requested a quantity of "
              + quantity);
    return true;
  }

  @Transactional
  public void updateItemQuantity(Item item, Integer quantity) {
    item.setAvailableQuantity(item.getAvailableQuantity() - quantity);
  }

  @Transactional
  public OrderResponse cancelOrder(ModifyOrderRequest request) {
    Order order =
        orderRepository
            .findById(request.orderId())
            .orElseThrow(() -> new OrderNotFoundException(request.orderId()));
    if (order.getUser().getId().equals(request.userId())) {
      order.setStatus("Canceled");
      return new OrderResponse(
          order.getId(),
          order.getUser().getId(),
          order.getOrderItems().stream().map(orderItem -> orderItem.getId()).toList(),
          order.getUser().getUsername(),
          order.getCreated_at());
    }
    throw new WrongOrderUserIDException(request.userId());
  }

  @Transactional
  public OrderResponse completeOrder(ModifyOrderRequest request) {
    Order order =
        orderRepository
            .findById(request.orderId())
            .orElseThrow(() -> new OrderNotFoundException(request.orderId()));
    if (order.getUser().getId().equals(request.userId())) {
      order.setStatus("Canceled");
      return new OrderResponse(
          order.getId(),
          order.getUser().getId(),
          order.getOrderItems().stream().map(orderItem -> orderItem.getId()).toList(),
          order.getUser().getUsername(),
          order.getCreated_at());
    }
    throw new WrongOrderUserIDException(request.userId());
  }
}
