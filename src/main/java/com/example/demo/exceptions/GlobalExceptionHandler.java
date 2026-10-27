
package com.example.demo.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle field validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Handle product not found
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFound(
            ProductNotFoundException ex) {

        return new ResponseEntity<>(
                ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Handle duplicate product ID
    @ExceptionHandler(DuplicateProductException.class)
    public ResponseEntity<String> handleDuplicateProduct(
            DuplicateProductException ex) {

        return new ResponseEntity<>(
                ex.getMessage(), HttpStatus.CONFLICT);
    }

    // Handle invalid arguments
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(
            IllegalArgumentException ex) {

        return new ResponseEntity<>(
                ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Handle other unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(
            Exception ex) {

        return new ResponseEntity<>(
                "An unexpected error occurred",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
