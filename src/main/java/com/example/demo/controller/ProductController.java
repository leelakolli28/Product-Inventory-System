
package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Add a product
    @PostMapping
    public ResponseEntity<Product> addProduct(
            @Valid @RequestBody Product product) {

        Product savedProduct = productService.addProduct(product);

        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    //View all products
    @GetMapping("/getallproducts")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Find product by ID
    @GetMapping("/byid")
    public Product getProductById(@RequestParam("id") Long id) {
        return productService.getProductById(id);
    }

    // Update product
    @PutMapping("/updateproduct/{id}")
    public Product updateProduct( @PathVariable Long id, @Valid @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    //  Delete product
    @DeleteMapping("/deleteproduct/{id}")
    public String deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    // Filter products by category
    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory( @PathVariable String category) {
        return productService.getProductsByCategory(category);
    }

    // Sort products by name
    @GetMapping("/sort/name")
    public List<Product> sortProductsByName() {
        return productService.sortProductsByName();
    }

    // Sort products by price
    @GetMapping("/sort/price")
    public List<Product> sortProductsByPrice() {
        return productService.sortProductsByPrice();
    }
    
    // finds stock less than or equal to 5.
    @GetMapping("/low-stock")
    public List<Product> getLowStockProducts() {
        return productService.getLowStockProducts();
    }
  
}
