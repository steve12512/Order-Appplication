package com.example.demo.service;

import com.example.demo.domain.Item;
import com.example.demo.dto.requests.item_requests.CreateItemRequest;
import com.example.demo.exception.item.ItemAlreadyExistsException;
import com.example.demo.exception.item.ItemNotFoundException;
import com.example.demo.repository.item.ItemRepository;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class ItemService {
  private final ItemRepository repository;

  public Item createItem(CreateItemRequest request) {
    String name = request.name();
    if (repository.findByName(name) != null)
      throw new ItemAlreadyExistsException("Item with id: " + request.name() + " already exists");
    Item item =
        Item.builder().name(request.name()).price(request.price()).info(request.info()).build();
    repository.save(item);
    return item;
  }

  public Item getItemById(Long id) {
    Item item = repository.findById(id);
    if (item == null) throw new ItemNotFoundException();
    return item;
  }

  public Item getItemByName(String name) {
    Item item = repository.findByName(name);
    if (item == null) throw new ItemNotFoundException();
    return item;
  }

  public Item saveItem(Item item) {
    repository.save(item);
    return item;
  }
}
