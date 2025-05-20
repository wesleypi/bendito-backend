package com.benditocupcake.src.controller;

import com.benditocupcake.src.controller.dto.request.product.ProductCreateRequest;
import com.benditocupcake.src.controller.dto.request.product.ProductUpdateRequest;
import com.benditocupcake.src.controller.dto.response.ProductPaginatedResponse;
import com.benditocupcake.src.controller.dto.response.ProductResponse;
import com.benditocupcake.src.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse createProduct(@Valid @RequestBody ProductCreateRequest request) {
        return productService.createProduct(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'MOTORIST')")
    public ProductResponse getProductById(@PathVariable @Min(1) Long id) {
        return productService.getProductById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'MOTORIST')")
    public ProductPaginatedResponse getAllProducts(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(required = false) String category) {
        return productService.getAllProducts(page, pageSize, category);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse updateProduct(
            @PathVariable @Min(1) Long id,
            @Valid @RequestBody ProductUpdateRequest request) {
        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deactivateProduct(
            @PathVariable @Min(1) Long id) {
        productService.deactivateProduct(id);
        return ResponseEntity.noContent().build();
    }
}
