
package com.example.demo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.exceptions.DuplicateProductException;

@Service
public class ProductService {

    // Temporary storage for products
    private final List<Product> products = new ArrayList<>();

    // Add a new product
    public Product addProduct(Product product) {

        boolean exists = products.stream()
                .anyMatch(p -> p.getProductId()
                        .equals(product.getProductId()));

        if (exists) {
            throw new DuplicateProductException(
                    "Product ID already exists: "
                            + product.getProductId());
        }

        products.add(product);
        return product;
    }

    // View all products
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    // Find product by ID
    public Product getProductById(Long productId) {

        return products.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product not found with ID: " + productId));
    }

    // Update product
    public Product updateProduct(Long productId, Product updatedProduct) {

        Product existingProduct = getProductById(productId);

        existingProduct.setProductName(updatedProduct.getProductName());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setStock(updatedProduct.getStock());

        return existingProduct;
    }

    // Delete product
    public String deleteProduct(Long productId) {

        Product product = getProductById(productId);
        products.remove(product);

        return "Product deleted successfully with ID: " + productId;
    }

    // Filter products by category
    public List<Product> getProductsByCategory(String category) {

        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException(
                    "Category cannot be blank");
        }

        return products.stream()
                .filter(p -> p.getCategory()
                        .equalsIgnoreCase(category))
                .toList();
    }

    // Sort products by name
    public List<Product> sortProductsByName() {

        return products.stream()
                .sorted(Comparator.comparing(
                        Product::getProductName,
                        String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    // Sort products by price (low to high)
    public List<Product> sortProductsByPrice() {

        return products.stream()
                .sorted(Comparator.comparing(Product::getPrice))
                .toList();
    }

    // Find products with stock of 5 or less
    public List<Product> getLowStockProducts() {

        return products.stream()
                .filter(p -> p.getStock() <= 5)
                .toList();
    }

}
