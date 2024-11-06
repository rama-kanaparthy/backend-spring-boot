package com.rama.backend_spring_boot.repository;

import com.rama.backend_spring_boot.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Derived query method based on method name
    List<Product> findByName(String name);

    List<Product> findByPriceGreaterThan(double price);

    // Using JPQL query to find products with price greater than a given value
    @Query("SELECT p FROM Product p WHERE p.price > :price")
    List<Product> findProductsWithPriceGreaterThan(@Param("price") double price);

    Page<Product> findByNameContaining(String name, Pageable pageable);

    /*Pageable: Contains information about the page number, size, and sorting.
            Page<T>: Represents a page of results, providing metadata like the total number of elements.*/
    Page<Product> findByPriceGreaterThan(double price, Pageable pageable);  // Pageable enables pagination
}

