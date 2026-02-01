package com.example.demo.exception;

import com.example.demo.dto.responses.user_responses.UserResponse;
import com.example.demo.exception.item.ItemAlreadyExistsException;
import com.example.demo.exception.item.ItemErrorResponse;
import com.example.demo.exception.order.OrderErrorResponse;
import com.example.demo.exception.order.OrderForUserNotFoundException;
import com.example.demo.exception.order.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.demo.exception.InvalidIdException;
import com.example.demo.exception.UserErrorResponse;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<?> handleInvalidException(InvalidIdException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new com.example.demo.exception.UserErrorResponse("Invalid Id", ex.getMessage(),HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(com.example.demo.exception.UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExists(com.example.demo.exception.UserAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new UserErrorResponse("User already exists",ex.getMessage(),HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler(com.example.demo.exception.UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(com.example.demo.exception.UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UserErrorResponse("User not found",ex.getMessage(),HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler
    public ResponseEntity<?> handleUserIsAlreadyInactiveException(com.example.demo.exception.UserisAlreadyInactiveException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new UserErrorResponse("User is already inactive", ex.getMessage(), HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler
    public ResponseEntity<?> handleItemAlreadyExistsException(ItemAlreadyExistsException ex){
        return ResponseEntity.status((HttpStatus.CONFLICT)).body(new ItemErrorResponse("Item already exists",HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler
    public ResponseEntity<?> handleOrderNotFoundException(OrderNotFoundException ex) {
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new OrderErrorResponse("Order not found",HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler
    public ResponseEntity<?> handleOrderForUserNotFoundException(OrderForUserNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new OrderErrorResponse("Order for user with id : " + ex.getUserId() + " was not found",HttpStatus.NOT_FOUND.value()));
    }



}
