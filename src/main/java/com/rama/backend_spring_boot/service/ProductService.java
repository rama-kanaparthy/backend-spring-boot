package com.rama.backend_spring_boot.service;

import com.rama.backend_spring_boot.exception.ProductAlreadyExistsException;
import com.rama.backend_spring_boot.exception.ProductNotFoundException;
import com.rama.backend_spring_boot.model.Product;
import com.rama.backend_spring_boot.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found."));
    }

    public Product createProduct(Product product) {
        if (productRepository.existsById(product.getId())) {
            throw new ProductAlreadyExistsException("Product with ID " + product.getId() + " already exists.");
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        if (productRepository.existsById(id)) {
            product.setId(id);
            return productRepository.save(product);
        }
        throw new ProductNotFoundException("Product with ID " + id + " not found.");
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
           throw new ProductNotFoundException("Product with ID " + id + " not found.");
        }
        productRepository.deleteById(id);
    }

    public Page<Product> getPagedProducts(double price, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByPriceGreaterThan(price, pageable);
    }

    // Method to get paged and sorted products
    public Page<Product> getPagedAndSortedProducts(int page, int size, String sortBy) {
        // Create a Pageable object with pagination and sorting
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return productRepository.findAll(pageable);
    }

    // Method to get paged products filtered by name
    public Page<Product> getProductsByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByNameContaining(name, pageable);
    }


}

