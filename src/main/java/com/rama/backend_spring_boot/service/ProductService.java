package com.rama.backend_spring_boot.service;

import com.rama.backend_spring_boot.exception.ProductAlreadyExistsException;
import com.rama.backend_spring_boot.exception.ProductNotFoundException;
import com.rama.backend_spring_boot.model.Product;
import com.rama.backend_spring_boot.model.dtos.ProductDTO;
import com.rama.backend_spring_boot.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Cacheable(value = "products", key = "'allProducts'")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Cacheable(value = "product", key = "#id")
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found."));
    }

    @CachePut(value = "product", key = "#product.id")
    public Product createProduct(ProductDTO product) {
        if (productRepository.existsById(product.getId())) {
            throw new ProductAlreadyExistsException("Product with ID " + product.getId() + " already exists.");
        }

        Product newProduct = new Product();
        newProduct.setId(product.getId());
        newProduct.setName(product.getName());
        newProduct.setPrice(product.getPrice());

        return productRepository.save(newProduct);
    }

    @CachePut(value = "product", key = "#id")
    public Product updateProduct(Long id, Product product) {
        if (productRepository.existsById(id)) {
            product.setId(id);
            return productRepository.save(product);
        }
        throw new ProductNotFoundException("Product with ID " + id + " not found.");
    }

    @CacheEvict(value = "product", key = "#id")
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
           throw new ProductNotFoundException("Product with ID " + id + " not found.");
        }
        productRepository.deleteById(id);
    }

    @Cacheable(value = "productsByPrice", key = "#price + '-' + #page + '-' + #size")
    public Page<Product> getPagedProducts(double price, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByPriceGreaterThan(price, pageable);
    }

    @Cacheable(value = "pagedProducts", key = "#page + '-' + #size + '-' + #sortBy")
    public Page<Product> getPagedAndSortedProducts(int page, int size, String sortBy) {
        // Create a Pageable object with pagination and sorting
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return productRepository.findAll(pageable);
    }

    @Cacheable(value = "productsByName", key = "#name + '-' + #page + '-' + #size")
    public Page<Product> getProductsByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByNameContaining(name, pageable);
    }
}


