package com.example.demo.controller;

import com.example.demo.dto.requests.item_requests.CreateItemRequest;
import com.example.demo.dto.responses.item_responses.ItemResponse;
import com.example.demo.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
@Validated
public class ItemController {

  private final ItemService itemService;

  public ItemController(ItemService itemService) {
    this.itemService = itemService;
  }

  @GetMapping()
  public ItemResponse getItem(@RequestParam String name) {
    return itemService.getItemByName(name);
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  @Validated
  public ItemResponse createItem(@Valid @RequestBody CreateItemRequest request) {
    return itemService.createItem(request);
  }
}
