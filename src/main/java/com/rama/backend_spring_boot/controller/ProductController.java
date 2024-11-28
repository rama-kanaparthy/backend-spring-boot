package com.rama.backend_spring_boot.controller;

import com.rama.backend_spring_boot.model.Product;
import com.rama.backend_spring_boot.model.dtos.ProductDTO;
import com.rama.backend_spring_boot.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Get all products", description = "Retrieve a list of all products available in the system.")
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @Operation(
            summary = "Get product by ID",
            description = "Retrieve a specific product by its ID. If the product doesn't exist, a 404 error is returned.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the product"),
                    @ApiResponse(responseCode = "404", description = "Product not found")
            }
    )

    @GetMapping("/{id}")
    public Product getProductById(
            @PathVariable
            @Parameter(description = "The ID of the product to retrieve") Long id) {
        return productService.getProductById(id);
    }

    @Operation(summary = "Create a new product", description = "Create a new product by providing product details in the request body.")
    @PostMapping
    public Product createProduct(@Valid @RequestBody @Parameter(description = "Product object that needs to be added to the inventory") ProductDTO productDTO) {
        log.info("Create Product {}",productDTO);
        return productService.createProduct(productDTO);
    }

    @Operation(
            summary = "Update product by ID",
            description = "Update the details of an existing product by its ID. If the product doesn't exist, a 404 error is returned.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated the product"),
                    @ApiResponse(responseCode = "404", description = "Product not found")
            }
    )
    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable
            @Parameter(description = "The ID of the product to update") Long id,
            @RequestBody
            @Parameter(description = "Product object with updated details") Product product) {
        return productService.updateProduct(id, product);
    }

    @Operation(
            summary = "Delete product by ID",
            description = "Delete an existing product by its ID. If the product is successfully deleted, a 200 status code is returned."
    )
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable @Parameter(description = "The ID of the product to be deleted") Long id) {
        productService.deleteProduct(id);
    }

    // Endpoint to get paginated and sorted products
    // GET /products/paged?page=0&size=5&sortBy=name
    @GetMapping("/paged")
    public Page<Product> getPagedAndSortedProducts(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy) {

        return productService.getPagedAndSortedProducts(page, size, sortBy);
    }

    // Endpoint to get paginated products filtered by name
    //GET /products/search?name=Product&page=0&size=5
    @GetMapping("/search")
    public Page<Product> getProductsByName(
            @RequestParam String name,
            @RequestParam int page,
            @RequestParam int size) {

        return productService.getProductsByName(name, page, size);
    }
}
