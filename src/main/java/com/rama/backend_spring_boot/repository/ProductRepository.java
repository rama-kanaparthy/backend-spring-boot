package com.rama.backend_spring_boot.repository;

import com.rama.backend_spring_boot.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

